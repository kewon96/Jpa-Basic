package org.example.b_relation_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.b_relation_mapping.model.Member2;
import org.example.b_relation_mapping.model.Team;

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
//            after(manager);
//            case1_story(manager);
//            case1_solution(manager);

            oneToMany(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void oneToMany(EntityManager manager) {
        Member2 member = new Member2("member1");
        manager.persist(member);

        // teamA는 그저 insert까지만 동작한다.
        Team team = new Team("teamA");

        // 하지만 이 부분에서 수정질의문이 동작하는데
        // 이것은 DB에서 정의된 관계 상 어쩔 수 없이 수정질의문이 동작하게 된다.
        team.getMembers().add(member);

        manager.persist(team);
    }

    /**
     * 상황 : 회원생성 후 팀을 생성하는데 이때 생성된 팀에 회원 주입
     * 결과 : "member1"이라는 회원에 "TeamA"외래키가 주입되지 않은 상황
     */
    private static void case1_story(EntityManager manager) {
        Member2 member = new Member2("member1");
        manager.persist(member);

        Team team = new Team("TeamA");
        team.getMembers().add(member);

        manager.persist(team);

        manager.flush();
        manager.clear();
    }

    /**
     * 해결방안 : Member에 Team을 넣어준다.
     * 근거 : Team에 걸려있는 관계는 그저 읽기전용으로 JPA에서 받아들이기 때문이다.
     */
    private static void case1_solution(EntityManager manager) {
        Team team = new Team("TeamA");
        manager.persist(team);

        Member2 member = new Member2("member2", team);
        manager.persist(member);

        manager.flush();
        manager.clear();
    }

    /**
     * 번외 : 여기서 flush & clear를 하지 않는다면???
     * <pre>
     *     solution에서라면 굳이 team에 속한 member를 주입할 필요가 없다.
     *     하지만 이때 flush & clear를 하지 않고 team에 속한 member목록을 가져온다면 이야기가 달라진다.
     *     이렇게 되면 1차캐시에 계속 머물러있을테니 당연히 tema에 속한 member목록을 가져올 때 member2는 존재하지 않게된다.
     *
     *     따라서, 만약의 사태를 위해 상위개념에도 데이터를 반영해주는 것이 좋다.(객체지향관점에서도 반영하는 것이 합당하다.)
     * </pre>
     */
    private static void case1_extra(EntityManager manager) {
        Team team = new Team("TeamA");
        manager.persist(team);

        Member2 member = new Member2("member2", team);
        manager.persist(member);

        // 귀찮으니 Member2 생성자에 병합시킴
//        team.getMembers().add(member);

        Team findTeam = manager.find(Team.class, team.getId());
        List<Member2> members = findTeam.getMembers();

        System.out.println("==================");
        for(Member2 m : members) {
            System.out.println("m = " + m.getUsername());
        }
        System.out.println("==================");
    }


    private static void after(EntityManager manager) {
        Team team = new Team("team1");
        manager.persist(team);

        Member2 member = new Member2("member1", team);
        manager.persist(member);

        manager.flush();
        manager.clear();

        Member2 findMember = manager.find(Member2.class, member.getId());
//        Team findTeam = findMember.getTeam();
//        List<Member2> members = findTeam.getMembers();

//        System.out.println(findTeam);
//        System.out.println(members);
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
