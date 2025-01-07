package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import jakarta.persistence.AttributeConverter;


public class UnitClassLevelAttributeConverter implements AttributeConverter<UnitClassLevel, String> {

    @Override
    public String convertToDatabaseColumn(UnitClassLevel attribute) {
        return attribute.name();
    }

    @Override
    public UnitClassLevel convertToEntityAttribute(String dbData) {
        return UnitClassLevel.getByName(dbData);
    }
}
