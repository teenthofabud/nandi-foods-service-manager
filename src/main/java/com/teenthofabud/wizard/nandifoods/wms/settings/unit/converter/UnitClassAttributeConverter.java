package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import jakarta.persistence.AttributeConverter;


public class UnitClassAttributeConverter implements AttributeConverter<UnitClass, String> {

    @Override
    public String convertToDatabaseColumn(UnitClass attribute) {
        return attribute.name();
    }

    @Override
    public UnitClass convertToEntityAttribute(String dbData) {
        return UnitClass.valueOf(dbData);
    }
}
