package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.mappers.UtilisateurMapper;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;
import org.recipe.originalyrecipe.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

        return utilisateurRepository.findById(aLong)
                .map(utilisateurMapper::entityToDTO)
                .orElseThrow(()-> new NoSuchElementException("Utilisateur non trouvé avec l'identifiant, essayer un autre identifint : " + aLong));
    }

    @Override
    public UtilisateurDTO add(UtilisateurForm toAdd) {
        Utilisateur utilisateur = utilisateurMapper.formToEntity(toAdd);
        return utilisateurMapper.entityToDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UtilisateurDTO update(Long aLong, UtilisateurUpdateForm utilisateurUpdateForm) {
        Utilisateur utilisateur= utilisateurRepository.findById(aLong).orElseThrow();
        utilisateur.setMail(utilisateurUpdateForm.getMail());
        utilisateur.setPays(utilisateurUpdateForm.getPays());
        return utilisateurMapper.entityToDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UtilisateurDTO remove(Long aLong) {
        Utilisateur utilisateur= utilisateurRepository.findById(aLong).orElseThrow();
        utilisateurRepository.delete(utilisateur);
        return utilisateurMapper.entityToDTO(utilisateur);
    }
}
