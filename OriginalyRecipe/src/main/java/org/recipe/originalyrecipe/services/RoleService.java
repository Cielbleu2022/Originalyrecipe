package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.models.entity.Role;
import org.recipe.originalyrecipe.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements BaseRoleService{
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role createRole(String name) {
        // Vérifiez si le nom du rôle est spécifié et est un rôle autorisé
        if (name != null && !name.trim().isEmpty() && isValidRole(name)) {
            // Vérifiez si le rôle existe déjà
            Role existingRole = findByName(name);
            if (existingRole != null) {
                return existingRole;
            }

            // Créez et enregistrez un nouveau rôle
            Role newRole = new Role(name);
            return roleRepository.save(newRole);
        } else {
            // Aucun nom de rôle spécifié ou rôle non autorisé, attribuer le rôle par défaut "client"
            Role defaultRole = findByName("client");
            if (defaultRole == null) {
                throw new IllegalStateException("Le rôle par défaut 'client' n'existe pas en base de données.");
            }
            return defaultRole;
        }
    }

    private boolean isValidRole(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Le nom de rôle est invalide s'il est null ou une chaîne vide
        }

        // Liste des rôles autorisés
        String[] allowedRoles = {"client", "admin", "partenaire"};

        // Vérifiez si le rôle spécifié est autorisé
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equalsIgnoreCase(name)) {
                return true;
            }
        }

        // Aucun rôle autorisé trouvé
        return false;
    }
}