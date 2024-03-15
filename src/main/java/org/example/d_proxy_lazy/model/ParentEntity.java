package org.example.d_proxy_lazy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Entity
@Getter @Setter
public class ParentEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 부모가 자식들을 직접적으로 관리할 때
    // ex: 게시물에 있는 첨부파일, 댓글, 등등
    // 단 자식이 다른곳에서도 연관되어있으면 피하자
    @OneToMany(mappedBy = "parentEntity", cascade = CascadeType.ALL)
    private List<ChildEntity> childEntityList = new ArrayList<>();

    public void addChild(ChildEntity child) {
        childEntityList.add(child);

        child.setParentEntity(this);
    }

}
