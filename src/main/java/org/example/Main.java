package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.model.Member;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // persistence.xml에 있는 persistence-unit의 name값을 넣어주면 된다.
        // EntityManagerFactory는 로딩시점에 딱 하나만 만들어져야한다.
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
            // 실행영역
            // JPA에서는 Transaction을 굉장히 중요하게 다룬다.
            // 데이터를 저장하는 행위에 있어서는 반드시 Transaction 안에서 처리되어야한다.

            // insert(manager);
//            update(manager);

//            List<Member> resultList = getMembers(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static List<Member> getMembers(EntityManager manager) {
        // DB의 Member를 대상으로 한게 아니라 Java의 Member를 대상으로 Query를 짠다. => 객체지향 질의문
        return manager.createQuery("SELECT m FROM Member as m", Member.class)
                // pagination
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
    }

    private static void update(EntityManager manager) {
        Member member = manager.find(Member.class, 1L);
        member.setName("helloA");

        // 수정할 때는 persist를 작동안해도 적용된다(마치 Java-Collection을 다루는 것 처럼...)
        // commit하지 직전에 변경점을 확인 후 각 변경점들을 반영시키고(update 실행) commit을 진행시킨다.
         manager.persist(member);

         member.setName("helloAA");
    }

    private static void insert(EntityManager manager) {
        Member member = new Member(2L, "helloB");
        manager.persist(member);
    }
}
