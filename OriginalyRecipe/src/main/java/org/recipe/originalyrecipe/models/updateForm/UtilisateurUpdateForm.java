package org.recipe.originalyrecipe.models.updateForm;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
@Getter
@Data
@Validated
@ToString
public class UtilisateurUpdateForm {
    @NotBlank
    private long id;
    @NotBlank
    private String mail;
    @NotBlank
    private String pays;
    private String roleName;
}
