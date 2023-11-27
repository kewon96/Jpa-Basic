package org.example.relation_mapping.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "member_2")
@Getter @Setter @NoArgsConstructor
public class Member2 {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

//    @Column(name = "team_id")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Member2(String username, Team team) {
        this.username = username;
        this.team = team;

        this.team.getMembers().add(this);
    }

    public Member2(String name) {
        this.username = name;
    }

}
