package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.models.entity.Role;

public interface BaseRoleService {
    Role findByName(String name);
    Role createRole(String name);
}
