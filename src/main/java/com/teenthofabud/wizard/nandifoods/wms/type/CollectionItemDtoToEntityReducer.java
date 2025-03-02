package com.teenthofabud.wizard.nandifoods.wms.type;

public interface CollectionItemDtoToEntityReducer<S,T> {

    /** Checks if there is already an entity with the id present in the source DTO
     * if present, fetches the existing entity object from DB/Cache
     * and updates the object with values from source DTO
     * or Else converts the given source DTO to the target entity
     * @param source object (instance of S) to convert
     * @return the converted object (must be an instance of T)
     * */
     T reduce(S source);

}
