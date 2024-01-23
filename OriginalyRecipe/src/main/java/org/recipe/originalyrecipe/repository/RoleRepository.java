package org.recipe.originalyrecipe.repository;

import org.recipe.originalyrecipe.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role save(Role role);
}
