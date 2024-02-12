package org.example.c_inherit_mapping.model;

import jakarta.persistence.Entity;
import lombok.Getter;

//@Entity
@Getter
public class Album extends Item {

    private String artist;

}
