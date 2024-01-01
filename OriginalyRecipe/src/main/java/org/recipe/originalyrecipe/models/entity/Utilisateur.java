package org.recipe.originalyrecipe.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name="utilisateur")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prénom;
    @Column(nullable = false)
    private Date dateDeNaissance;
    @Column(nullable = false)
    private String pays;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String motDePasse;

}
