package org.example.relation_mapping.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "member_2")
@Getter @Setter @NoArgsConstructor
public class RelationMappingMember {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "team_id")
    private Long teamId;

    public RelationMappingMember(String username, Long teamId) {
        this.username = username;
        this.teamId = teamId;
    }
}
