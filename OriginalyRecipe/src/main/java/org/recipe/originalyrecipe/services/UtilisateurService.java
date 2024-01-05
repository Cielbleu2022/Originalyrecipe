package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.mappers.UtilisateurMapper;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;
import org.recipe.originalyrecipe.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UtilisateurService implements BaseService<UtilisateurDTO,Long, Utilisateur, UtilisateurForm, UtilisateurUpdateForm>{
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
    }


    @Override
    public List<UtilisateurDTO> getAll() {

        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO findOne(Long aLong) {
        return null;
    }

    @Override
    public UtilisateurDTO add(UtilisateurForm toAdd) {
        return null;
    }

    @Override
    public UtilisateurDTO update(Long aLong, UtilisateurUpdateForm utilisateurUpdateForm) {
        return null;
    }

    @Override
    public UtilisateurDTO remove(Long aLong) {
        return null;
    }
}
