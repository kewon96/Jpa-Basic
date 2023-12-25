package org.example.b_relation_mapping.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

//    @ManyToMany(mappedBy = "products")
//    private List<Member2> members = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
