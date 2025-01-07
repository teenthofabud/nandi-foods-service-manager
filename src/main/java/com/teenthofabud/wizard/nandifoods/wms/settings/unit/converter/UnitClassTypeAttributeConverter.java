package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import jakarta.persistence.AttributeConverter;


public class UnitClassTypeAttributeConverter implements AttributeConverter<UnitClassType, String> {

    @Override
    public String convertToDatabaseColumn(UnitClassType attribute) {
        return attribute.name();
    }

    @Override
    public UnitClassType convertToEntityAttribute(String dbData) {
        return UnitClassType.getByName(dbData);
    }
}
