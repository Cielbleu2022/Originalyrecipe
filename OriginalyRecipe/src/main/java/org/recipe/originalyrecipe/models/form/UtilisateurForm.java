package org.recipe.originalyrecipe.models.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@Validated
@ToString
public class UtilisateurForm {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotNull
    private Date dateDeNaissance;
    @NotBlank
    private String pays;
    @NotBlank
    private String mail;
    @NotBlank
    private String motDePasse;
    @Getter
    private String roleName;

}
