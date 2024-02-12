package org.example.b_relation_mapping.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

//@Entity
@Getter
public class MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member2 member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime orderDateTime;



}
