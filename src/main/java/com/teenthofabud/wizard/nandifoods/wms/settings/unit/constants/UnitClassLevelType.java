package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import com.teenthofabud.wizard.nandifoods.wms.constants.EnumKeyValue;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum UnitClassLevelType implements EnumKeyValue<UnitClassLevelType> {

    @FieldNameConstants.Include LEVEL_1_EACH(UnitClassLevel.LEVEL_1, UnitClassType.EACH),
    @FieldNameConstants.Include LEVEL_2_BOX(UnitClassLevel.LEVEL_2, UnitClassType.BOX),
    @FieldNameConstants.Include LEVEL_3_CARTON(UnitClassLevel.LEVEL_3, UnitClassType.CARTON),
    @FieldNameConstants.Include LEVEL_4_CASE(UnitClassLevel.LEVEL_4, UnitClassType.CASE),
    @FieldNameConstants.Include LEVEL_5_CRATE(UnitClassLevel.LEVEL_5, UnitClassType.CRATE),
    @FieldNameConstants.Include LEVEL_6_T_TOTE(UnitClassLevel.LEVEL_6, UnitClassType.T_TOTE),
    @FieldNameConstants.Include LEVEL_7_PALLET(UnitClassLevel.LEVEL_7, UnitClassType.PALLET),
    @FieldNameConstants.Include LEVEL_8_TEU(UnitClassLevel.LEVEL_8, UnitClassType.TEU);

    private UnitClassLevel level;
    private UnitClassType type;

    private UnitClassLevelType(UnitClassLevel level, UnitClassType type) {
        this.level = level;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getLevel().name() + ": " + this.getType().name();
    }

    public static List<UnitClassLevel> getAllLevels() {
        return Arrays.stream(values()).map(v -> v.getLevel()).collect(Collectors.toList());
    }

    public static List<UnitClassType> getAllTypes() {
        return Arrays.stream(values()).map(v -> v.getType()).collect(Collectors.toList());
    }

    public static UnitClassLevelType getByLevel(UnitClassLevel level) throws NoSuchElementException {
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

    public static UnitClassLevelType getByLevel(String level) throws NoSuchElementException {
        UnitClassLevelType x = null;
        for(UnitClassLevelType t : UnitClassLevelType.values()) {
            if(t.getLevel().getName().compareTo(level) == 0) {
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

    public static UnitClassLevelType getByType(UnitClassType type) throws NoSuchElementException {
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

    public static UnitClassLevelType getByType(String type) throws NoSuchElementException {
        UnitClassLevelType x = null;
        for(UnitClassLevelType t : UnitClassLevelType.values()) {
            if(t.getType().getName().compareTo(type.toUpperCase()) == 0) {
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

    @Override
    public String getKey() {
        return this.level.name();
    }

    @Override
    public String getValue() {
        return this.type.name();
    }

    @Override
    public List<EnumKeyValue<UnitClassLevelType>> getEnumConstants() {
        return List.of(UnitClassLevelType.values());
    }

    @Override
    public String getKeyName() {
        return "level";
    }

    @Override
    public String getValueName() {
        return "type";
    }

    @Override
    public String getTypeName() {
        return UnitClassLevelType.class.getSimpleName();
    }
}
