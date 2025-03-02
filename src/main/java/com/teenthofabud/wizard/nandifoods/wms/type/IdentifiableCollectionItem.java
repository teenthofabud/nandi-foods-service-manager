package com.teenthofabud.wizard.nandifoods.wms.type;

public interface IdentifiableCollectionItem<T> {
    // Must return the id field which is annotated with either JaVers @Id or JPA @Id annotations
    public T getID();
}
