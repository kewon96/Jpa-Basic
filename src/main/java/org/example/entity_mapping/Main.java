package org.example.entity_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.entity_mapping.model.EntityMappingMember;

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
        EntityMappingMember entityMappingMember = manager.find(EntityMappingMember.class, 90651L);
        entityMappingMember.setName("asf,flbj");

        manager.detach(entityMappingMember);

        EntityMappingMember entityMappingMember1 = manager.find(EntityMappingMember.class, 90651L);
        entityMappingMember1.setName("asf,flbj");

    }

    private static void randomMember(EntityManager manager) {
        manager.persist(EntityMappingMember.random());
    }

    private static void dirtyChecking(EntityManager manager) {
        // 89374

        EntityMappingMember entityMappingMember = manager.find(EntityMappingMember.class, 89374L);
        System.out.println(entityMappingMember);

        entityMappingMember.setName("ZZZZZ");

    }

    private static void lazyTransaction(EntityManager manager, EntityTransaction tx) {
        //엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
        manager.persist(EntityMappingMember.random());
        manager.persist(EntityMappingMember.random());
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
        EntityMappingMember findEntityMappingMember1 = manager.find(EntityMappingMember.class, 3L);
        EntityMappingMember findEntityMappingMember2 = manager.find(EntityMappingMember.class, 3L);
        System.out.println(findEntityMappingMember1);
        System.out.println(findEntityMappingMember2);

        System.out.println(findEntityMappingMember1 == findEntityMappingMember2);

    }

    private static List<EntityMappingMember> getMembers(EntityManager manager) {
        // DB의 Member를 대상으로 한게 아니라 Java의 Member를 대상으로 Query를 짠다. => 객체지향 질의문
        return manager.createQuery("SELECT m FROM EntityMappingMember as m", EntityMappingMember.class)
                // pagination
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
    }

    private static void update(EntityManager manager) {
        EntityMappingMember entityMappingMember = manager.find(EntityMappingMember.class, 1L);
        entityMappingMember.setName("helloA");

        // 수정할 때는 persist를 작동안해도 적용된다(마치 Java-Collection을 다루는 것 처럼...)
        // commit하지 직전에 변경점을 확인 후 각 변경점들을 반영시키고(update 실행) commit을 진행시킨다.
         manager.persist(entityMappingMember);

         entityMappingMember.setName("helloAA");
    }

    private static void insert(EntityManager manager) {
        EntityMappingMember random = EntityMappingMember.random();
        manager.persist(random);
    }

    private static void inserts(EntityManager manager) {
        LinkedList<EntityMappingMember> entityMappingMembers = EntityMappingMember.createMembers();
        System.out.println("==================");
        entityMappingMembers.forEach(manager::persist);

        entityMappingMembers.forEach(x -> System.out.println(x.getId()));
        System.out.println("==================");
    }
}