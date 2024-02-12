package org.example.b_relation_mapping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter @NoArgsConstructor
public class Team {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    /**
     * JPA에서 mappedBy로 지정된 컨텐츠는 아예 반영하지를 않는다.
     *  => 그저 읽기전용
     */
    @OneToMany(mappedBy = "team")
    private List<Member2> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
