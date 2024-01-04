package org.recipe.originalyrecipe.mappers;
public interface BaseMapper<DTO,Entity,Form,UpdateForm> {
    DTO entityToDTO(Entity entity);
    Entity formToEntity (Form form);
    Entity formUpdateToEntity(UpdateForm updateForm);
}
