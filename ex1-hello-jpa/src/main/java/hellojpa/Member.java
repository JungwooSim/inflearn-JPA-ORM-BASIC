package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임 (Ex. MySQL 에서 AUTOINCREMENT)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE) // 유일한 값을 순서대로 생성 (EX. oracle sequence)
//    @GeneratedValue(strategy = GenerationType.TABLE) // 키 생성 전용 TABLE 생성하여 데이터베이스 시퀀스 흉내 (DB를 하나 더 생성하기 때문에 성능에 단점)
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
