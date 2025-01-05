package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoCollection;
import org.javers.core.diff.custom.CustomValueComparator;

public class UnitClassMeasuredValuesDtoCollectionComparator implements CustomValueComparator<UnitClassMeasuredValuesDtoCollection> {

    @Override
    public boolean equals(UnitClassMeasuredValuesDtoCollection a, UnitClassMeasuredValuesDtoCollection b) {
        return (a.getMeasuredValues().isEmpty() && b.getMeasuredValues().isEmpty()
                || a.getMeasuredValues().isEmpty() && !b.getMeasuredValues().isEmpty()
                || !a.getMeasuredValues().isEmpty() && b.getMeasuredValues().isEmpty()
                || !a.getMeasuredValues().isEmpty() && !b.getMeasuredValues().isEmpty());
    }

    @Override
    public String toString(UnitClassMeasuredValuesDtoCollection value) {
        return value.getMeasuredValues().toString();
    }
}