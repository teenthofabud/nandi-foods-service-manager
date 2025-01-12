package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoV2;
import lombok.Builder;
import org.javers.core.diff.custom.CustomValueComparator;

@Builder
public class UnitClassMeasuredValuesDtoV2Comparator implements CustomValueComparator<UnitClassMeasuredValuesDtoV2> {

    @Override
    public boolean equals(UnitClassMeasuredValuesDtoV2 a, UnitClassMeasuredValuesDtoV2 b) {
        return a.equals(b);
    }

    @Override
    public String toString(UnitClassMeasuredValuesDtoV2 value) {
        return super.toString();
    }

}