package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import jakarta.persistence.AttributeConverter;


public class UnitClassLevelAttributeConverter implements AttributeConverter<UnitClassLevelType, String> {

    @Override
    public String convertToDatabaseColumn(UnitClassLevelType attribute) {
        return attribute.getLevel();
    }

    @Override
    public UnitClassLevelType convertToEntityAttribute(String dbData) {
        return UnitClassLevelType.getByLevel(dbData);
    }
}
