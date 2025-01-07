package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum UnitClassType {

    @FieldNameConstants.Include EACH( "EACH"),
    @FieldNameConstants.Include BOX( "BOX"),
    @FieldNameConstants.Include CARTON( "CARTON"),
    @FieldNameConstants.Include CASE( "CASE"),
    @FieldNameConstants.Include CRATE( "CRATE"),
    @FieldNameConstants.Include T_TOTE( "T_TOTE"),
    @FieldNameConstants.Include PALLET( "PALLET"),
    @FieldNameConstants.Include TEU( "TEU");

    private String name;

    private UnitClassType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static List<String> getAllNames() {
        return Arrays.stream(values()).map(v -> v.getName()).collect(Collectors.toList());
    }

    public static UnitClassType getByName(String name) throws NoSuchElementException {
        UnitClassType x = null;
        for(UnitClassType t : UnitClassType.values()) {
            if(t.getName().compareTo(name.toUpperCase()) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Class Type available by name: " + name);
        }
    }
}
