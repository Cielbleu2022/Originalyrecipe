package org.recipe.originalyrecipe.models.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ToString
public class UtilisateurForm {
    @NotBlank
    private String pays;
    @NotBlank
    private String mail;
    @NotBlank
    private String motDePasse;
}
