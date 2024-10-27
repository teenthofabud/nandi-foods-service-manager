package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import jakarta.persistence.AttributeConverter;


public class UnitClassLevelTypeAttributeConverter implements AttributeConverter<UnitClassLevelType, String> {

    @Override
    public String convertToDatabaseColumn(UnitClassLevelType attribute) {
        return attribute.getType();
    }

    @Override
    public UnitClassLevelType convertToEntityAttribute(String dbData) {
        return UnitClassLevelType.getByType(dbData);
    }
}
