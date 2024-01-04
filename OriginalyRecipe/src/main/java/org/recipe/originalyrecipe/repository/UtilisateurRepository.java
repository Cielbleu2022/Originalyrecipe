package org.recipe.originalyrecipe.repository;

import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
}
