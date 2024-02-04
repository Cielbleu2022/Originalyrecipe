package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;

import javax.naming.AuthenticationException;
import java.util.List;

public interface BaseService <DTO,ID,Entity,Form,UpdateForm>{
    List<DTO> getAll();
    DTO findOne(ID id);
    DTO add(Form toAdd);
    DTO update(ID id,UpdateForm updateForm);
    DTO remove(ID id);
    List<DTO> searchByName(String nom);

    UtilisateurDTO findByMail(String mail);
    UtilisateurDTO login(String email, String password) throws AuthenticationException;


    void logout();
}
