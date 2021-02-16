# inflearn-JPA-ORM-BASIC
인프런 강의 / 자바 ORM 표준 JPA 프로그래밍 - 기본편 (김영한)

> **현재 우리는 프로그램을 개발할 때 객체지향적으로 진행한다.**<br>
> **이에 따라 관계형 데이터베이스와 프로그램과 적절한 다리를 이어줄수 있는 것이 필요하다.**<br>
> **그것이 JPA 이다.**

### SQL 중심적인 개발의 문제점
- CRUD의 반복
- 자바 객체 -> SQL (반복)
- SQL -> 자바객체 (반복)
- SQL에 의존적인 개발을 피하기 어렵다.
- 객체 그래프 탐색이 어렵다.
  - 처음 실행하는 SQL에 따라 탐색 범위가 결정되기 때문
- 계층형 아키텍처가 힘들다.
  - 진정한 의미의 계층분할이 힘들다.

**가장 중요한 점은 패러다임 불일치!!**
- 객체는 추상화, 캡슐화, 정보은닉, 상속, 다형성 등 시스템의 복잡성을 제어할 수 있는 다양한 장치를 제공한다. 이를 활용하여 개발하는 것이 목적이지만
- 관계형 데이터베이스는 정형화를 통해 각 테이블의 관계를 만들어 자료를 저장하는 것이 목적이다.

### JPA - Java Persistence API
- 자바 진영의 ORM 기술의 표준
  - ORM - Object-Relations Mapping
    - 객체는 객체대로 설계, 관계형 데이터베이스는 데이터베이스 대로 설계
    - ORM 프레임워크가 중간에서 매핑
    - 대중적인 언어에는 대부분 ORM 기술 존재
- JPA는 인터페이스의 모음
  - Hibernate, EcilpseLink, DataNuclus

### JPA 사용목적
- SQL 중심적인 개발에서 객체 중심적으로 개발
- 생산성, 유지보수
- 패러다임 불일치 해결
  - 상속
  - 연관관계
  - 객체 그래프 탐색
- 패러다임 불일치 해결
- 성능
  - 1차 캐시와 동일성(identity) 보장
    - 같은 트랜잭션 안에서는 같은 엔티티를 반환 -> 약간의 조회 성능 향상
    - DB Isolation Level 이 Read Commit 이어도 어플리케이션 Repeatable Read 보장
  - 트랜잭션을 지원하는 쓰기 지연 (transactional write-behind)
    1. 트랜잭션을 커밋할 때까지 Insert SQL 을 모음
    2. JDBC BATCH SQL 기능을 사용하여 한번에 SQL 전송
  - 지연 로딩 (Lazy Loading)
    - 지연 로딩 : 객체가 실제 사용될 때 로딩
    - 즉시 로딩 : Join SQL 로 한번에 연관된 객체까지 미리 조회
- 데이터 접근 추상화와 벤더 독립성
- 표준

### 엔티티 매니저 팩토리와 엔티티 매니저
<img src="https://github.com/JungwooSim/inflearn-JPA-ORM-BASIC/blob/master/img/img-1.png" width="500px;">
- 팩토리에서 매니저를 생성하여 할당

#### 영속성 컨텍스트
- "엔티티를 영구저장하는 환경" 이라는 뜻
- 영속성 컨텍스트는 논리적인 개념
- 엔티티 매니저를 통해 영속성 컨텍스트에 접근

#### 엔티티 생명주기
- 비영속 (new / transient) : 영속성 컨텍스트와 전체 관계가 없는 새로운 상태
- 영속 (managed) : 영속성 컨텍스트에 관리되는 상태
- 준영속 (detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제 (removed) : 삭제된 상태
<img src="https://github.com/JungwooSim/inflearn-JPA-ORM-BASIC/blob/master/img/img-2.png" width="500px;">

#### 영속성 컨텍스트의 이점
- 1차 캐시
  - 캐시 범위는 하나의 트랜잭션 안에서에 한함
- 동일성 (identity) 보장
  - 1차 캐시도 반복 가능한 읽기 (REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공
- 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
- 변경 감지 (Dirty Checking)
- 지연 로딩 (Lazy Loading)
