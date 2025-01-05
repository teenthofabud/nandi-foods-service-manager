package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.OptionalUnitClassMeasuredValuesDtoCollection;
import org.javers.core.diff.custom.CustomValueComparator;

public class OptionalUnitClassMeasuredValuesDtoCollectionComparator implements CustomValueComparator<OptionalUnitClassMeasuredValuesDtoCollection> {

    @Override
    public boolean equals(OptionalUnitClassMeasuredValuesDtoCollection a, OptionalUnitClassMeasuredValuesDtoCollection b) {
        return (a.getMeasuredValues().isEmpty() && b.getMeasuredValues().isEmpty())
                || (a.getMeasuredValues().isPresent() && b.getMeasuredValues().isEmpty())
                || (a.getMeasuredValues().isEmpty() && b.getMeasuredValues().isPresent())
                || (a.getMeasuredValues().isPresent() && b.getMeasuredValues().isPresent()
                    ? a.getMeasuredValues().get().containsAll(b.getMeasuredValues().get())
                && b.getMeasuredValues().get().containsAll(a.getMeasuredValues().get()) : false);
    }

    @Override
    public String toString(OptionalUnitClassMeasuredValuesDtoCollection value) {
        return value.getMeasuredValues().isPresent() ? value.getMeasuredValues().get().toString() : "";
    }
}