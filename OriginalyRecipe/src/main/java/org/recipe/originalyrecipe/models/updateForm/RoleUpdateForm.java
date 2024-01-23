package org.recipe.originalyrecipe.models.updateForm;

import jakarta.validation.constraints.NotBlank;

public class RoleUpdateForm {
    @NotBlank
    private String name;
}
