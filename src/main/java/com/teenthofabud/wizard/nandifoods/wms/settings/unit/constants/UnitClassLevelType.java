package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum UnitClassLevelType {

    @FieldNameConstants.Include LEVEL_1("Level 1", "EACH"),
    @FieldNameConstants.Include LEVEL_2("Level 2", "BOX"),
    @FieldNameConstants.Include LEVEL_3("Level 3", "CARTON"),
    @FieldNameConstants.Include LEVEL_4("Level 4", "CASE"),
    @FieldNameConstants.Include LEVEL_5("Level 5", "CRATE"),
    @FieldNameConstants.Include LEVEL_6("Level 6", "T_TOTE"),
    @FieldNameConstants.Include LEVEL_7("Level 7", "PALLET"),
    @FieldNameConstants.Include LEVEL_8("Level 8", "TEU");

    private String level;
    private String type;

    private UnitClassLevelType(String level, String type) {
        this.level = level;
        this.type = type;
    }

    @Override
    public String toString() {
        return level;
    }

    public static List<String> getAllLevels() {
        return Arrays.stream(values()).map(v -> v.getLevel()).collect(Collectors.toList());
    }

    public static List<String> getAllTypes() {
        return Arrays.stream(values()).map(v -> v.getType()).collect(Collectors.toList());
    }

    public static UnitClassLevelType getByLevel(String level) throws NoSuchElementException {
        UnitClassLevelType x = null;
        for(UnitClassLevelType t : UnitClassLevelType.values()) {
            if(t.getLevel().compareTo(level) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Level Type available by level: " + level);
        }
    }

    public static UnitClassLevelType getByType(String type) throws NoSuchElementException {
        UnitClassLevelType x = null;
        for(UnitClassLevelType t : UnitClassLevelType.values()) {
            if(t.getType().compareTo(type) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Level Type available by type: " + type);
        }
    }

}
