package org.example.e_value_type.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class Address {

    private String city;
    private String detail;
    private String zipcode;

    public Address(String city, String detail, String zipcode) {
        this.city = city;
        this.detail = detail;
        this.zipcode = zipcode;
    }

    public static Address copy(Address address) {
        return new Address(address.getCity(), address.getDetail(), address.getZipcode());
    }

//    private Member4 member;
}
