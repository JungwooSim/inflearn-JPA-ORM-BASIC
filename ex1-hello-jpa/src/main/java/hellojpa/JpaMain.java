package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
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

            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList(); // JPQL - 객체를 쿼리함.

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); // 실제 어플리케이션이 끝나면 close 필요
    }
}
