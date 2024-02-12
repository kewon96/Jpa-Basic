package org.example.d_proxy_lazy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ChildEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 양방향으로 해봄
    @ManyToOne
    @JoinColumn(name = "parent_entity_id")
    private ParentEntity parentEntity;

}
