package org.example.e_value_type.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor
public class Member4 {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "age")
    private int age;

    // Period
    @Embedded
    private Period workPeriod;


    // Address
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name="home_city")),
            @AttributeOverride(name = "detail", column = @Column(name="home_detail")),
            @AttributeOverride(name = "zipcode", column = @Column(name="home_zipcode"))
    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name="work_city")),
            @AttributeOverride(name = "detail", column = @Column(name="work_detail")),
            @AttributeOverride(name = "zipcode", column = @Column(name="work_zipcode"))
    })
    private Address workAddress;

    public Member4(String username) {
        this.username = username;
    }
}
