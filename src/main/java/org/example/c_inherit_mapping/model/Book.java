package org.example.c_inherit_mapping.model;

import jakarta.persistence.Entity;
import lombok.Getter;

@Getter @Entity
public class Book extends Item {

    private String author;

    private String isbn;

}
