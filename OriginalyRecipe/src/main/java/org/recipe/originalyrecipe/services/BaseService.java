package org.recipe.originalyrecipe.services;

import java.util.List;

public interface BaseService <DTO,ID,Entity,Form,UpdateForm>{
    List<DTO> getAll();
    DTO findOne(ID id);
    DTO add(Form toAdd);
    DTO update(ID id,UpdateForm updateForm);
    DTO remove(ID id);
    List<DTO> searchByName(String nom);

}
