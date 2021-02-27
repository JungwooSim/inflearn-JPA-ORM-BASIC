package hellojpa;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
//    @ManyToOne(fetch = FetchType.EAGER) // 즉시로딩
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    /*
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
    */
}
