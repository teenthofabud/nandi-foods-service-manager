    package com.teenthofabud.wizard.nandifoods.wms.dto;


    import com.teenthofabud.wizard.nandifoods.wms.type.CollectionItemDtoToEntityReducer;
    import com.teenthofabud.wizard.nandifoods.wms.type.IdentifiableCollectionItem;
    import lombok.Builder;
    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.lang.Nullable;

    import java.util.List;

    @Getter
    @Setter
    @Builder
    public class ComparisonConfigDto<S extends IdentifiableCollectionItem,T extends IdentifiableCollectionItem> {

        /** Actual collection field in the entity which needs to be updated */
        List<T> entityCollectionField;

        @Nullable
        CollectionItemDtoToEntityReducer<S,T> reducer;

        /** class of items in the DTO list*/
        Class<S> sourceClass;

        /** List of old DTO items*/
        List<S> oldCollection;

        /** List of new DTO items*/
        List<S> newCollection;

    }
