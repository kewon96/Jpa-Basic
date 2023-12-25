package org.example.b_relation_mapping.model;

import jakarta.persistence.*;


@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Member2 member;

}
