package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.diffplug.common.base.Errors;
import com.teenthofabud.wizard.nandifoods.wms.dto.ComparisonDetailsDto;
import com.teenthofabud.wizard.nandifoods.wms.type.CollectionItemDtoToEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.type.IdentifiableCollectionItem;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public interface ComparitveCollectionUpdateHandler {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ComparativeUpdateHandler.class);



    default <S extends IdentifiableCollectionItem,T extends IdentifiableCollectionItem> List<T> comparativelyUpdateCollectionFields(Diff diff, List<T> listFieldRef, @Nullable CollectionItemDtoToEntityReducer<S,T> reducer, Class<S> sourceClass){

        log.debug("All changes : {}",diff.getChanges());
        // All new items in the dto list will be added to the entity collection field after reducing
        diff.getChangesByType(NewObject.class).forEach(Errors.rethrow().wrap(p->{
           S newObj = sourceClass.cast(p.getAffectedObject().get());
           log.debug("New object : {}",newObj);
           T converted = reducer.reduce(newObj);
           listFieldRef.add(converted);
        }));

        // All items which aren't present in the dto list will be removed from entity collection field
        diff.getChangesByType(ObjectRemoved.class).forEach(Errors.rethrow().wrap(p->{
            S removedObj = sourceClass.cast(p.getAffectedObject().get());
            log.debug("Object to be removed : {}",removedObj);
            listFieldRef.removeIf(e -> removedObj.getID().equals(e.getID()));
        }));

        // Last case when an existing entry is modified to have new values
        // (Except its ID field in which case it will be considered as a new object and old one will be removed)
        diff.getChangesByType(ValueChange.class).forEach(Errors.rethrow().wrap(p->{
            // changes of type InitialValueChange and TerminalValueChange has to be avoided
            // as they are already incorporated in NewObject and ObjectRemoved types
            if (!(p.getClass().equals(InitialValueChange.class) || p.getClass().equals(TerminalValueChange.class))) {
                S changedObj = sourceClass.cast(p.getAffectedObject().get());
                T targetObj = listFieldRef.stream().filter(e->e.getID().equals(changedObj.getID())).findFirst().get();
                getBeanUtilsBean().copyProperty(targetObj,p.getPropertyName(),p.getRight());
            }
        }));

        return listFieldRef;
    }

    default <S extends IdentifiableCollectionItem,T extends IdentifiableCollectionItem> List<T> comparativelyUpdateCollectionFieldsV2(ComparisonDetailsDto comparisonConfig){

        Class<S> sourceClass = comparisonConfig.getSourceClass();
        List<S> old = comparisonConfig.getOldCollection();
        List<S> _new = comparisonConfig.getNewCollection();
        CollectionItemDtoToEntityReducer<S,T> reducer = comparisonConfig.getReducer();
        List<T> listFieldRef = comparisonConfig.getEntityCollectionField();
        Diff diff = javers().compareCollections(old,_new,sourceClass);

        log.debug("All changes : {}",diff.getChanges());
        // All new items in the dto list will be added to the entity collection field after reducing
        diff.getChangesByType(NewObject.class).forEach(Errors.rethrow().wrap(p->{
            S newObj = sourceClass.cast(p.getAffectedObject().get());
            log.debug("New object : {}",newObj);
            T converted = reducer.reduce(newObj);
            listFieldRef.add(converted);
        }));

        // All items which aren't present in the dto list will be removed from entity collection field
        diff.getChangesByType(ObjectRemoved.class).forEach(Errors.rethrow().wrap(p->{
            S removedObj = sourceClass.cast(p.getAffectedObject().get());
            log.debug("Object to be removed : {}",removedObj);
            listFieldRef.removeIf(e -> removedObj.getID().equals(e.getID()));
        }));

        // Last case when an existing entry is modified to have new values
        // (Except its ID field in which case it will be considered as a new object and old one will be removed)
        diff.getChangesByType(ValueChange.class).forEach(Errors.rethrow().wrap(p->{
            // changes of type InitialValueChange and TerminalValueChange has to be avoided
            // as they are already incorporated in NewObject and ObjectRemoved types
            if (!(p.getClass().equals(InitialValueChange.class) || p.getClass().equals(TerminalValueChange.class))) {
                S changedObj = sourceClass.cast(p.getAffectedObject().get());
                T targetObj = listFieldRef.stream().filter(e->e.getID().equals(changedObj.getID())).findFirst().get();
                getBeanUtilsBean().copyProperty(targetObj,p.getPropertyName(),p.getRight());
            }
        }));
        return listFieldRef;
    }

    BeanUtilsBean getBeanUtilsBean();

    default Javers javers(){
        return JaversBuilder.javers().build();
    };

}
