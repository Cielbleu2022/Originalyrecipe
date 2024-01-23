package org.recipe.originalyrecipe.models.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable = false, unique = true)
    private String name;

    public Role() {
        // Par défaut, un rôle sans nom
    }

    public Role(String name) {
        this.name = name;
    }

}
