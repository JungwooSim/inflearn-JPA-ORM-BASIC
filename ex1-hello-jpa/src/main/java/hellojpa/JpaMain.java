package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// xml 에서 설정한 값, db당 하나만 생성

        EntityManager em = emf.createEntityManager(); // 쓰레드간에 공유하면 안됨. 사용하고 버려야 함.

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 작성 순서 - 5 고급매핑 - 상속관계 메핑
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);
            tx.commit();
            /*
            // 작성 순서 - 4 양방향 연관관계와 연관관계의 주인
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }
            tx.commit();
             */

            /*
            # 작성 순서 - 3
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId()); // left join 하여 가져옴. (현재는)
            Team findTeam = findMember.getTeam();

            System.out.println("findTeamName = " + findTeam.getName());
            tx.commit();
             */

            /*
            작성 순서 - 2
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
            tx.commit();
             */
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); // 실제 어플리케이션이 끝나면 close 필요

        /*
        작성 순서 - 1

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// xml 에서 설정한 값, db당 하나만 생성

        EntityManager em = emf.createEntityManager(); // 쓰레드간에 공유하면 안됨. 사용하고 버려야 함.

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // save
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

            // find
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

            // update
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");

            // JPQL
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList(); // JPQL - 객체를 쿼리함.

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }
            // JPQL END

            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); // 실제 어플리케이션이 끝나면 close 필요
         */

    }
}
