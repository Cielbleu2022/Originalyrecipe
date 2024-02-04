package org.recipe.originalyrecipe.repository;

import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    List<Utilisateur> findByNomContainingIgnoreCase(String nom);
    Utilisateur findByMail(String mail);
}
