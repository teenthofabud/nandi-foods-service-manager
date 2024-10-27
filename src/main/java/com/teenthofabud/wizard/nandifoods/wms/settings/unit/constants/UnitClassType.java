package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.NoSuchElementException;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum UnitClassType {

    @FieldNameConstants.Include UOM(1),
    @FieldNameConstants.Include PU(2),
    @FieldNameConstants.Include HU(3);

    private Integer ordinal;

    private UnitClassType(Integer ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return name();
    }


    public static UnitClassType getByOrdinal(Integer ordinal) throws NoSuchElementException {
        UnitClassType x = null;
        for(UnitClassType t : UnitClassType.values()) {
            if(t.getOrdinal().compareTo(ordinal) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Class Type available by ordinal: " + ordinal);
        }
    }

}
