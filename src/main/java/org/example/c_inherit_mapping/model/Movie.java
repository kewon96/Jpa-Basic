package org.example.c_inherit_mapping.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
@Entity
public class Movie extends Item {

    private String director;

    private String actor;

    public Movie(String name, int price, String director, String actor) {
        super(name, price);

        this.director = director;
        this.actor = actor;
    }

}
