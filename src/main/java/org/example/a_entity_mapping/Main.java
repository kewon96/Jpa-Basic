package org.example.a_entity_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.a_entity_mapping.model.Member1;

import java.util.LinkedList;
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

//             insert(manager);
             inserts(manager);
//            update(manager);

//            List<Member> resultList = getMembers(manager);

//            cache_1_dept(manager);
//            lazyTransaction(manager,tx);
//            dirtyChecking(manager);

//            randomMember(manager);

//            detachMember(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void detachMember(EntityManager manager) {
        Member1 member1 = manager.find(Member1.class, 90651L);
        member1.setName("asf,flbj");

        manager.detach(member1);

        Member1 member11 = manager.find(Member1.class, 90651L);
        member11.setName("asf,flbj");

    }

    private static void randomMember(EntityManager manager) {
        manager.persist(Member1.random());
    }

    private static void dirtyChecking(EntityManager manager) {
        // 89374

        Member1 member1 = manager.find(Member1.class, 89374L);
        System.out.println(member1);

        member1.setName("ZZZZZ");

    }

    private static void lazyTransaction(EntityManager manager, EntityTransaction tx) {
        //엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
        manager.persist(Member1.random());
        manager.persist(Member1.random());
        //여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
        //커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
    }

    private static void cache_1_dept(EntityManager manager) {
//        Member member = new Member();
//        member.setId(3L);
//        member.setName("회원1");
//        //1차 캐시에 저장됨
//        manager.persist(member);

        //1차 캐시에서 조회
        Member1 findMember11 = manager.find(Member1.class, 3L);
        Member1 findMember12 = manager.find(Member1.class, 3L);
        System.out.println(findMember11);
        System.out.println(findMember12);

        System.out.println(findMember11 == findMember12);

    }

    private static List<Member1> getMembers(EntityManager manager) {
        // DB의 Member를 대상으로 한게 아니라 Java의 Member를 대상으로 Query를 짠다. => 객체지향 질의문
        return manager.createQuery("SELECT m FROM Member1 as m", Member1.class)
                // pagination
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
    }

    private static void update(EntityManager manager) {
        Member1 member1 = manager.find(Member1.class, 1L);
        member1.setName("helloA");

        // 수정할 때는 persist를 작동안해도 적용된다(마치 Java-Collection을 다루는 것 처럼...)
        // commit하지 직전에 변경점을 확인 후 각 변경점들을 반영시키고(update 실행) commit을 진행시킨다.
         manager.persist(member1);

         member1.setName("helloAA");
    }

    private static void insert(EntityManager manager) {
        Member1 random = Member1.random();
        manager.persist(random);
    }

    private static void inserts(EntityManager manager) {
        LinkedList<Member1> member1s = Member1.createMembers();
        System.out.println("==================");
        member1s.forEach(manager::persist);

        member1s.forEach(x -> System.out.println(x.getId()));
        System.out.println("==================");
    }
}
