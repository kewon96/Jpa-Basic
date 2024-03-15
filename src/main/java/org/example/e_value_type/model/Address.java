package org.example.e_value_type.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

//    private Member4 member;
}
