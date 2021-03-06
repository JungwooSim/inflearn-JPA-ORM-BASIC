### 연관관계 매핑
- 객체와 테이블 연관관계의 차이
- 객체의 참조와 테이블 외래키를 매핑
- 용어
  - 방향(Direction) : 단방향, 양방향
  - 다중성(Multiplicity) : 다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:N) 이해
  - 연관관계 주인(Owner) : 객체 양방향 연관관계는 관리 주인이 필요
    
**양방향 매핑 규칙**<br>
- 객체의 두 관계중 하나를 연관관계의 주인으로 지정
- 연관관계 주인만이 외래 키를 관리(등록, 수정)
- 주인이 아닌쪽은 읽기만 가능
- 주인은 mappedBy 속성을 사용하지 않음
- 주인이 아니라면 mappedBy 속성으로 주인 지정
> 연관관계의 주인은 외래키가 존재하는 곳이 된다.

**양방향 매핑 정리**<br>
- 단방향 매핑만으로 이미 연관관계 매핑은 완료
- 양방향 매핑은 반대 방향으로 조회기능이 추가된 것 뿐
- JPQL에서 역방향으로 탐색할 일이 많음
- 단방향 매핑을 잘하고 양방향은 필요할 때 추가해도 됨 (테이블에 영향 주지 않음)
> 단방향만 미리 설계 후 양방향은 필요로할 때 추가하면 된다.

---
### 실전 예제 3 - 관계형 데이터베이스와 객체 스키마
<img src="/jpashop/img/img-1.png" width="500px;">

- 엔티티 관계

<img src="/jpashop/img/img-2.png" width="500px;">

- ERD

<img src="/jpashop/img/img-3.png" width="500px;">

- 엔티티 상세

---
### 실전 예제 4 - 상속관계 매핑

<img src="/jpashop/img/img-4.png" width="500px;"><br>
<img src="/jpashop/img/img-5.png" width="500px;"><br>
<img src="/jpashop/img/img-6.png" width="500px;"><br>
