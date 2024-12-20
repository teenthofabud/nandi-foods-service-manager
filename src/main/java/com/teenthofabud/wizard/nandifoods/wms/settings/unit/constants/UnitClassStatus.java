package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.NoSuchElementException;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum UnitClassStatus {


    @FieldNameConstants.Include PENDING(0),
    @FieldNameConstants.Include ACTIVE(1),
    @FieldNameConstants.Include HOLD(2),
    @FieldNameConstants.Include CLOSED(3);

    @Override
    public String toString() {
        return name();
    }

    private Integer ordinal;

    private UnitClassStatus(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public static UnitClassStatus getByOrdinal(Integer ordinal) throws NoSuchElementException {
        UnitClassStatus x = null;
        for(UnitClassStatus t : UnitClassStatus.values()) {
            if(t.getOrdinal().compareTo(ordinal) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Class Status available by ordinal: " + ordinal);
        }
    }

}
