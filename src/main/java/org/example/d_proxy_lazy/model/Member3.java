package org.example.d_proxy_lazy.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


//@Entity
//@Table(name = "member_3")
@Getter @Setter @NoArgsConstructor @ToString
public class Member3 {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team2 team;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Member3 member3 = (Member3) o;
        return Objects.equals(id, member3.id) && Objects.equals(username, member3.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, team);
    }
}
