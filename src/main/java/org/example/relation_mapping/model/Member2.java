package org.example.relation_mapping.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member_2")
@Getter @Setter @NoArgsConstructor
public class Member2 {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "locker_id") // joinColumn의 기본값이 있긴한데 굉장히 난해해서 직접등록하는게 이롭다.
    private Locker locker;

//    @ManyToMany
//    @JoinTable(name = "member_product")
//    private List<Product> products = new ArrayList<>();

    @OneToMany
    private List<MemberProduct> products = new ArrayList<>();

    public Member2(String username, Team team) {
        this.username = username;
        this.team = team;

        this.team.getMembers().add(this);
    }

    public Member2(String name) {
        this.username = name;
    }

}
