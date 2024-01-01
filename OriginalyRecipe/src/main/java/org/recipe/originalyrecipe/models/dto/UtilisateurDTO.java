package org.recipe.originalyrecipe.models.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prénom;
    private Date dateDeNaissance;
    private String pays;
    private String mail;
    private String motDePasse;

}
