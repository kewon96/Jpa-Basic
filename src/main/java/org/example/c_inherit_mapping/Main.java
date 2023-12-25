package org.example.c_inherit_mapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.b_relation_mapping.model.Member2;
import org.example.b_relation_mapping.model.Team;
import org.example.c_inherit_mapping.model.Item;
import org.example.c_inherit_mapping.model.Movie;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hello");
        // EntityManager는 행위(데이터를 추가한다, 데이터를 조회한다, 등등)를 할 때 마다 만들어져야한다. => Data Connection을 받았다고 생각해도 무방
        EntityManager manager = factory.createEntityManager();

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        try {

            join_strategy(manager);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            manager.close();
        }

        factory.close();
    }

    private static void join_strategy(EntityManager manager) {
        Movie movie = new Movie("Movie1", 10000,"Director1", "actor1");
//        Item item = new Item("Item1", 2000);

        manager.persist(movie);
//        manager.persist(item);

        initPersist(manager);

        Movie movie1 = manager.find(Movie.class, movie.getId());
        System.out.println(movie1);
    }

    private static void initPersist(EntityManager manager) {
        manager.flush();
        manager.clear();
    }
}
