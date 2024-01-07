package org.recipe.originalyrecipe.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
/**
 * Entité représentant un utilisateur dans le système.
 */
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
    @Column(nullable = false, name = "nom")
    private String nom;

    @Column(nullable = false, name = "prenom")
    private String prenom;

    @Column(nullable = false, name = "date_de_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateDeNaissance;

    @Column(nullable = false, name = "pays")
    private String pays;

    @Column(nullable = false, name = "mail")
    private String mail;

    @Column(nullable = false, name = "mot_de_passe")
    private String motDePasse;
}


