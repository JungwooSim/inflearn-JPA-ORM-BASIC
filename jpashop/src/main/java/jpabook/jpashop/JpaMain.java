package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// xml 에서 설정한 값, db당 하나만 생성

        EntityManager em = emf.createEntityManager(); // 쓰레드간에 공유하면 안됨. 사용하고 버려야 함.

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close(); // 실제 어플리케이션이 끝나면 close 필요
    }
}
