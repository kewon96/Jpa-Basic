package org.example.e_value_type;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.e_value_type.model.Address;
import org.example.e_value_type.model.Member4;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            Address homeAddressByMember1 = new Address("고양시", "원흥1로", "00000");

            int age = 20;
            Member4 member1 = new Member4("member1");
            member1.setHomeAddress(homeAddressByMember1);
            member1.setAge(age);
            manager.persist(member1);

            Address homeAddressByMember2 = Address.copy(homeAddressByMember1);

            Member4 member2 = new Member4("member2");
            // 위처럼 값을 복사해놨다한들 member2.setHomeAddress(member1.getAddress());로 사용된 것을 Compile에서 막을 방도가 없음
            member2.setHomeAddress(homeAddressByMember2);
            member2.setAge(age);
            manager.persist(member2);

            // 이렇게 되면 member1의 주소와 member2의 주소가 같이 변경되는 Side Effect가 연출된다.
            // 정말 만약에 일괄적으로 수정하고싶으면 "주소"라는 값타입을 Entity로서 만들어서 사용해야한다.
            member1.getHomeAddress().setCity("서울시");

            // 단, Java의 기본타입의 경우 값이 항상 복사되기에 영향이 끼치지않는다.
            member1.setAge(30);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        factory.close();
    }
}
