package org.recipe.originalyrecipe.mappers;

import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;

public class MapperUtilisateur implements BaseMapper<UtilisateurDTO, Utilisateur, UtilisateurForm, UtilisateurUpdateForm> {

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


        return utilisateur;

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

        if (utilisateurUpdateForm.getMotDePasse() != null && !utilisateurUpdateForm.getMotDePasse().isEmpty()) {
            utilisateur.setMotDePasse(utilisateurUpdateForm.getMotDePasse());
        };

        return utilisateur;
    }
}
