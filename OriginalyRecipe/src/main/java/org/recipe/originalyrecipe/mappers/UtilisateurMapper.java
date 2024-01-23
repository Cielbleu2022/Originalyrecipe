package org.recipe.originalyrecipe.mappers;

import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.entity.Role;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;
import org.recipe.originalyrecipe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurMapper implements BaseMapper<UtilisateurDTO, Utilisateur, UtilisateurForm, UtilisateurUpdateForm> {

    private final RoleRepository roleRepository;

    @Autowired
    public UtilisateurMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public UtilisateurDTO entityToDTO(Utilisateur utilisateur) {
        if(utilisateur==null)return null;
        UtilisateurDTO utilisateurDTO= UtilisateurDTO.builder()
                    .id(utilisateur.getId())
                    .nom(utilisateur.getNom())
                    .prenom(utilisateur.getPrenom())
                    .dateDeNaissance(utilisateur.getDateDeNaissance())
                    .pays(utilisateur.getPays())
                    .mail(utilisateur.getMail())
                    .build();


        utilisateurDTO.setRole(utilisateur.getRole() != null ? utilisateur.getRole().getName() : null);


        return utilisateurDTO;
    }

    @Override
    public Utilisateur formToEntity(UtilisateurForm utilisateurForm) {
        if (utilisateurForm == null)return null;
        Utilisateur utilisateur = new Utilisateur();
        if (utilisateurForm.getNom() != null && !utilisateurForm.getNom().isEmpty()) {
            utilisateur.setNom(utilisateurForm.getNom());
        }

        if (utilisateurForm.getPrenom() != null && !utilisateurForm.getPrenom().isEmpty()) {
            utilisateur.setPrenom(utilisateurForm.getPrenom());
        }

        if (utilisateurForm.getDateDeNaissance() != null) {
            utilisateur.setDateDeNaissance(utilisateurForm.getDateDeNaissance());
        }

        if (utilisateurForm.getPays() != null && !utilisateurForm.getPays().isEmpty()) {
            utilisateur.setPays(utilisateurForm.getPays());
        }

        if (utilisateurForm.getMail() != null && !utilisateurForm.getMail().isEmpty()) {
            utilisateur.setMail(utilisateurForm.getMail());
        }

        if (utilisateurForm.getMotDePasse() != null && !utilisateurForm.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(utilisateurForm.getMotDePasse());
        }

        // Récupérer le rôle à partir du repository
        String roleName = utilisateurForm.getRoleName();
        System.out.println("RoleName from request: " + roleName);

        Role role = null;

// Vérifier si le roleName est spécifié et est un rôle autorisé
        if (roleName != null && !roleName.isEmpty() && isValidRole(roleName)) {
            // Rôle spécifié et autorisé, récupérer le rôle à partir du repository
            role = roleRepository.findByName(roleName);
        } else {
            // Aucun rôle spécifié ou rôle non autorisé, attribuer le rôle par défaut "client"
            role = roleRepository.findByName("client");
        }

        utilisateur.setRole(role);


        return utilisateur;

    }
    private boolean isValidRole(String name) {
        // Liste des rôles autorisés
        String[] allowedRoles = {"client", "admin", "partenaire"};

        // Vérifiez si le rôle spécifié est autorisé
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Utilisateur formUpdateToEntity(UtilisateurUpdateForm utilisateurUpdateForm) {
        if(utilisateurUpdateForm==null)return null;
        Utilisateur utilisateur = new Utilisateur();
        if (utilisateurUpdateForm.getMail() != null && !utilisateurUpdateForm.getMail().isEmpty()) {
            utilisateur.setMail(utilisateurUpdateForm.getMail());
        }

        if (utilisateurUpdateForm.getPays() != null && !utilisateurUpdateForm.getPays().isEmpty()) {
            utilisateur.setPays(utilisateurUpdateForm.getPays());
        }

        return utilisateur;
    }
}
