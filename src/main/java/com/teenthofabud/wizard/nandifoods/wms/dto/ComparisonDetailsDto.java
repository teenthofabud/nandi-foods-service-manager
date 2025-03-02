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
public class ComparisonDetailsDto<S extends IdentifiableCollectionItem,T extends IdentifiableCollectionItem> {

    List<T> entityCollectionField;

    @Nullable
    CollectionItemDtoToEntityReducer<S,T> reducer;

    Class<S> sourceClass;

    List<S> oldCollection;

    List<S> newCollection;

}
