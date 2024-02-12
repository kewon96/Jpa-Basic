package org.example.d_proxy_lazy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.d_proxy_lazy.model.ChildEntity;
import org.example.d_proxy_lazy.model.Member3;
import org.example.d_proxy_lazy.model.ParentEntity;
import org.example.d_proxy_lazy.model.Team2;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {
//            extracted1(manager);

            extracted2(manager);


            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void extracted2(EntityManager manager) {
        ChildEntity child1 = new ChildEntity();
        ChildEntity child2 = new ChildEntity();

        ParentEntity parent = new ParentEntity();

        parent.addChild(child1);
        parent.addChild(child2);

        // 하지만? 이렇게 하는게 아니라 Parent를 중심으로 구현하고 싶단 말이지?
        // 다시말해 이런식으로 persist를 일일히 진행시키는게 아니라
        // parent를 적용하면 하위에 연결된 child도 자연스레 적용하고 싶어짐
        manager.persist(parent);

        manager.flush();
        manager.clear();

        ParentEntity findParent = manager.find(ParentEntity.class, parent.getId());
        findParent.getChildEntityList().remove(0);

        manager.persist(findParent);
//            manager.persist(child1);
//            manager.persist(child2);
    }

    private static void extracted1(EntityManager manager) {
        // 기본데이터 생성
        Member3 member = new Member3();
        member.setUsername("user1");

        Team2 team2 = new Team2();
        team2.setName("team1");

        member.setTeam(team2);

        manager.persist(member);
        manager.persist(team2);

        manager.flush();
        manager.clear();

//            Member3 member3_1 = manager.getReference(Member3.class, member.getId());
//
//            System.out.println(member3_1.getClass()); // Hibernate가 만든 가짜Class -> Proxy Class
//            System.out.println(member3_1.getId());
//            System.out.println(member3_1.getUsername());
//
//            manager.flush();
//            manager.clear();

        System.out.println("===================");

        Member3 member3 = manager.find(Member3.class, member.getId());
        Team2 team = member3.getTeam();
        System.out.println(team.getName());
        System.out.println(team.getClass());
        //            System.out.println(member3.getTeam());


        // fetch-Lazy로 설정하고 이 상황에서 team도 같이 조회한다 왜지???
//            Member3 member3 = manager.getReference(Member3.class, member.getId());
//            System.out.println(member3.getTeam());
    }
}
