package hellojpa;

import javax.persistence.*;

@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;


    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
//    @Column(name = "TEAM_ID")
//    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Long getTeamId() {
//        return teamId;
//    }

//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

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
