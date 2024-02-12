package org.example.c_inherit_mapping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn
@Getter @NoArgsConstructor
public class Item extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

}
