package org.example.relation_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.relation_mapping.model.RelationMappingMember;
import org.example.relation_mapping.model.Team;

import java.lang.reflect.Member;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            doit(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void doit(EntityManager manager) {
        Team team = new Team("team1");
        manager.persist(team);

        RelationMappingMember member = new RelationMappingMember("member1", team.getId());
        manager.persist(member);

        Member findMember = manager.find(Member.class, member.getId());
        Team findTeam = manager.find(Team.class, member.getTeamId());

    }
}
