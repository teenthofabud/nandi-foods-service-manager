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
public enum UnitClassLevel {

    @JsonProperty("Level 1")
    @FieldNameConstants.Include LEVEL_1("Level 1"),
    @JsonProperty("Level 2")
    @FieldNameConstants.Include LEVEL_2("Level 2"),
    @JsonProperty("Level 3")
    @FieldNameConstants.Include LEVEL_3("Level 3"),
    @JsonProperty("Level 4")
    @FieldNameConstants.Include LEVEL_4("Level 4"),
    @JsonProperty("Level 5")
    @FieldNameConstants.Include LEVEL_5("Level 5"),
    @JsonProperty("Level 6")
    @FieldNameConstants.Include LEVEL_6("Level 6"),
    @JsonProperty("Level 7")
    @FieldNameConstants.Include LEVEL_7("Level 7"),
    @JsonProperty("Level 8")
    @FieldNameConstants.Include LEVEL_8("Level 8");

    private String name;

    private UnitClassLevel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static List<String> getAllLevels() {
        return Arrays.stream(values()).map(v -> v.getName()).collect(Collectors.toList());
    }

    public static UnitClassLevel getByName(String name) throws NoSuchElementException {
        UnitClassLevel x = null;
        for(UnitClassLevel t : UnitClassLevel.values()) {
            if(t.getName().compareTo(name) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No Unit Class Level available by name: " + name);
        }
    }
}
