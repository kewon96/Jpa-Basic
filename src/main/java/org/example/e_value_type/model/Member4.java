package org.example.e_value_type.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Member4 {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    // Period
    @Embedded
    private Period workPeriod;
//    private LocalDate startDate;
//    private LocalDate endDate;


    // Address
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name="home_city")),
            @AttributeOverride(name = "street", column = @Column(name="home_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name="home_zipcode"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name="work_city")),
            @AttributeOverride(name = "street", column = @Column(name="work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name="work_zipcode"))
    })
    private Address workAddress;
//    private String city;
//    private String street;
//    private String zipcode;

}
