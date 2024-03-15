package org.example.d_proxy_lazy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table(name = "member3_team")
@Getter @NoArgsConstructor @Setter
public class Team2 {

    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    /**
     * JPA에서 mappedBy로 지정된 컨텐츠는 아예 반영하지를 않는다.
     *  => 그저 읽기전용
     */
    @OneToMany(mappedBy = "team")
    private List<Member3> members = new ArrayList<>();
}
