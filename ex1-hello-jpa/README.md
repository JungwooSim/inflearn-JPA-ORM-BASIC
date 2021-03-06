### 상속관계 매핑
- 객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑

#### 슈퍼타입, 서브타입 논리 모델을 실제 물리 모델로 구현하는 방법
- <b>각 테이블로 변환 -> 조인 전략</b>
  - 장점
    - 테이블 정규화
    - 외래 키 참조 무경성 제약조건 활용가능
    - 저장공간 효율화
  - 단점
    - 조회시 조인을 많이 사용 -> 성능 저하
    - 조리 쿼리가 복잡
    - 데이터 저장시 Insert SQL 2번 발생 (큰 문제는 되지 않음)
> 서비스가 복잡하고 확장을 염두하고 있을때 사용하면 유용하다.
<img src="/ex1-hello-jpa/img/img-1.png" width="500px;">

- <b>통합 테이블로 변환 -> 단일 테이블 전략</b>
  - 장점
    - 조인이 필요 없다. -> 조회 성능이 빠르다.
    - 조회 쿼리가 단순하다.
  - 단점
    - 자식 엔티티가 매핑한 컬럼은 모두 null 허용
    - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. <br>상황에 따라서 조회 성능이 오히려 늦어질 수 있다. (한계점을 넘으면 발생하는 이슈인데, 한계점을 넘기가 쉽지 않다.)
> 서비스가 단순할때 사용하면 유용하다.

<img src="/ex1-hello-jpa/img/img-2.png" width="500px;">

- <b>서브타입 테이블로 변환 -> 구현 테이블마다 테이블 전략</b>
  - 장점
    - 서브 타입을 명확하게 구분해서 처리할 때 효과적
    - not null 제약조건 사용 가능
  - 단점
    - 여러 자식 테이블을 함께 조회할 때 성능이 느리다.(UNION SQL 필요)
    - 자식 테이블을 통합해서 쿼리하기 어렵다.
> 데이터베이스 설계자와 ORM 전문가 모두가 추천하지 않는 전략이다.

<img src="/ex1-hello-jpa/img/img-3.png" width="500px;">

#### 주요 어노테이션
- @Inheritance(strategy = InheritanceType.XXX)
  - JOINED : 조인 전략
  - SINGLE_TABLE : 단일 테이블 전략
  - TABLE_PER_CLASS : 구현 클래스마다 테이블 전략 
- @DiscriminatorColumn(name="DTYPE")
- @DiscriminatorValue("XXX")

---
### @MappedSuperclass : 공통 매핑 정보가 필요할 때 사용 
- 상속관계 매핑 X
- 엔티티 X -> 테이블과 매핑 X
- 상속 받는 자식 클래스에 매핑 정보만 제공
- 조회, 검색 불가( em.find(BaseEntity) 불가 )
- 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장

#### 간단한 정리
- 테이블과 관계 없고, 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모우는 역할
- 주로 등록일, 수정일, 등록자, 수정자 같은 전체 엔티티에서 공통으로 정보를 모을 때 사용
> 참고 : @Entity 클래스는 @Entity, @MappedSuperclass 로 지정한 클래스만 상속 가능하다.

---
### 8. 프록시와 연관관계 관리
#### 프록시 기초
- em.find() vs em.getReference()
- em.find() : 데이터베이스를 통해서 **실제 엔티티 객체 조회**
- em.getReference() : 데이터베이스 조회를 미루는 **가짜(프록시) 엔티티 객체 조회**

#### 프록시 특징
- 실제 클래스를 **상속** 받아서 만들어 진다. -> 타입 체크시 주의해야 한다.(== 비교 대신 instance of 사용)
- 실제 클래스와 **겉 모양이 같다.**
- **사용하는 입장에서는** 진짜 객체인지 프록시 객체인지 **구분하지 않고 사용**하면 된다.
- 프록시 객체는 실제 객채의 **참조(target)를 보관**
- **프록시 객체를 호출하면** 프록시 객체는 **실제 객체의 메소드 호출**
- 프록시 객체는 **처음 사용할 때 한번만 초기화**한다.
- 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다.<br>초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능하다.
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면, em.getReference()를 호출해도 실제 엔티티 반환 한다.
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 떼, 프록시를 초기화하면 문제 발생<br>(하이버네이트는 org.hibernate.LazyInitializationException 예외 발생)

#### 즉시 로딩과 지연 로딩
- 지연 로딩을 사용하면 프록시 엔티티 객체를 조회하게 된다. 그 후 실제로 사용하는 시점에 실제 객체의 메소드를 호출하게 된다.<br> (프록시를 응용한 방법이라고 보면 된다.)

#### 프록시와 즉시로딩 주의
- 가급적 지연 로딩만 사용해야 한다.
  - 즉시 로딩을 적용하면 예상하지 못한 SQL 발생
  - 즉시 로딩은 JPQL 에서 N+1 문제를 발생 시킨다.<br> -> 기본 엔티티를 조회하고 즉시로딩 설정된 엔티티가 있으면 해당 엔티티를 또 조회하게 된다.
  - 이를 해결하는 방법은 1. JPQL fetch 조인을 쓰거나, 2. 엔티티 그래프 기능을 사용하는 것이다. (뒤에 설) 
- @ManyToOne, @OneToOne 은 기본이 즉시 로딩(EAGER)로 설정되어 있다. -> 즉 지연 로딩(LAZY)로 재설정 필요.
- @OneToMany, @ManyToMany 는 기본이 지연 로딩(LAZY)로 설정되어 있다.

#### 영속성 전이 : CASCADE
- 특정 엔티티를 영속 상태로 만들때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때 사용<br> EX. 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장

#### 영속성 전이 : CASCADE - 주의할 점
- 영속성 전이는 연관관계를 매핑하는 것과 아무 관련 없다.<br>엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공 할 뿐이다.

#### 영속성 전이 : CASCADE - 종류
- ALL : 모두 적용
- PERSIST : 영속
- REMOVE : 삭제
- MERGE : 병합
- PERFESH : PERFESH
- DETACH : DETACH
> 언제사용 하는가?<br>
> 하나의 부모가 자식을 관리할 때 (단일 소유)<br>
> Ex. 게시판 등록시 첨부파일도 함께 등록 -> 해당 게시물에서만 첨부파일 관리<br>
> 만약, 파일을 다른 엔티티에서도 관리한다면 사용해서는 안된다.

#### 고아 객체
- 고아 객체 제거 : 부모 엔티티와 **연관관계가 끊어진 자식 엔티티를 자동으로 삭제**
- 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능
- orphanRemoval = true

#### 고아 객체 - 주의할 점
- 참조하는 곳이 하나일 때 사용해야 한다. -> 특정 엔티티가 개인 소유할 때 사용
- @OneToOne, @OneToMany 만 가능
- 개념적으로 부모를 제거하면 자식은 고아가 된다.<br> 따라서 고아 객체 제거 기능을 활성화하면, 부모를 제거할 때 자식도 제거 된다.<br>
이것은 Cascade.Type.REMOVE 처럼 동작 한다.

---
### 9. 값 타입
#### JPA 의 데이터 타입 분류
- 엔티티 타입
  - @Entity로 정의되는 객체
  - 데이터가 변해도 **식별자로 지속해서 추적 가능**
    - Ex. 회원 엔티티가 키, 나이 값을 변경해도 식별자로 인식 가능
- 값 타입
  - int, Integer, String 처럼 단순히 값으로 사용하는 자바 기본 타입이나 객체
  - **식별자가 없고** 값만 있으므로 변경시 추적 불가
    - Ex. 숫자 100을 200으로 변경하면 완전히 다른 값으로 대체됨
  
#### 값 타입 분류
- 기본값 타입
  - 생명주기를 엔티티의 의존
    - Ex. 회원을 삭제하면 이름, 나이 필드도 함께 삭제
  - 값 타입은 공유하면 X
    - Ex. 회원 이름 변경시 다른 회원의 이름도 함께 변경되면 안된다
  - Ex
    - 자바 기본 타입(int, double)
    - 레퍼 클래스(Integer, Long)
    - String
> 자바의 기본 타입은 절대로 공유하면 안된다.<br>
> int, double 같은 기본 타입(primitive type)은 절대 공유가 안된다. <- 항상 값을 복사하기 때문(즉 마음 놓고 사용해도 된다.)<br>
> Integer 와 같은 래퍼 클래스나 String 같은 클래스는 공유가능한 객체이지만 변경해서는 안된다. (참조값이 넘어감)
- 임베디드 타입(embedded type, 복합 값 타입)
  - 새로운 값 타입을 직접 정의 가능
  - 주로 기본 값 타입을 모아서 만들기 때문에 복합 값 타입이라고도 함
  - int, String 와 같은 값 타입 -> 추적 불가능
  - 사용법
    - @Embeddable : 값 타입을 정의하는 곳에 표시
    - @Embedded : 값 타입을 사용하는 곳에 표시
    - 기본 생성자 필수
  - 장점
    - 재사용성
    - 높은 응집도 (응집도 : 하나의 프로그램을 구성하는 각각의 모듈이 그 고유의 기능을 잘 처리할 수 있는지를 나타내는 정도)
  - 임베디드 타입과 테이블 매핑
    - 임베디드 타입은 **엔티티의 값일 뿐**이다.
    - 임베디드 타입을 **사용하기 전과 후에 매핑하는 테이블**은 같다.
    - 객체와 테이블을 **아주 세밀하게(find-grained) 매핑하는 것이 가능**하다.
    - **잘설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많다.**
  - 하나의 엔티티에서 같은 값 타입을 사용하기 위한 방법
    - @AttributeOverrides, @AttributeOverride 를 사용해서 컬럼명 속성을 재정의 하면 된다. 
- 컬렉션 값 타입(collection value type)

### 값 타입과 불변 객체
- 정의 : 값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념<br> 따라서 값 타입은 단순화하고 안전하게 다룰 수 있어야 한다.
- 값 타입 공유 참조
  - 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다. 
  - 참조를 공유하기 때문에 의도하지 않은 value 가 변경되는 side effect 발생할 수 있다.
  - 그렇기 때문에 값(인스턴스)을 복사해서 사용하는 것을 권장한다. (권장이라기 보다 무조건이다.)
- 객체 타입의 한계
  - 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입이다. (자바의 기본 타입은 무조건 값을 복사하지만 객체 타입은 주소를 참조하는 방식)
  - 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
  - 위와 같은 문제를 원천 차단하기 위해 **불변 객체**로 수정할 수 없도록 만들어야 한다.
    - 값 타입은 불변 객체(immutable object)로 설계해야 한다.
    - **불변 객체란 생성 시점 이후 절대 값을 변경할 수 없는 객체이다.**
    - 생성자로만 값을 설정하고 수정자를 만들지 않으면 된다.
    - 참고 : Integer, String 는 자바가 제공하는 대표적인 불변 객체 이다.
    - **불변이라는 작은 제약으로 부작용(side effect)을 막을 수 있다.**
