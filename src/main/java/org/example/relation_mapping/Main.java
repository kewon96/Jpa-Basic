package org.example.relation_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.relation_mapping.model.RelationMappingMember;
import org.example.relation_mapping.model.Team;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

//            before(manager);
            after(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void after(EntityManager manager) {
        Team team = new Team("team1");
        manager.persist(team);

        RelationMappingMember member = new RelationMappingMember("member1", team);
        manager.persist(member);

        manager.flush();
        manager.clear();

        RelationMappingMember findMember = manager.find(RelationMappingMember.class, member.getId());
        Team findTeam = findMember.getTeam();
        List<RelationMappingMember> members = findTeam.getMembers();

        System.out.println(findTeam);
        System.out.println(members);
    }

//    private static void before(EntityManager manager) {
//        Team team = new Team("team1");
//        manager.persist(team);
//
//        RelationMappingMember member = new RelationMappingMember("member1", team.getId());
//        manager.persist(member);
//
//        RelationMappingMember findMember = manager.find(RelationMappingMember.class, member.getId());
//        Team findTeam = manager.find(Team.class, member.getTeamId());
//
//    }
}
