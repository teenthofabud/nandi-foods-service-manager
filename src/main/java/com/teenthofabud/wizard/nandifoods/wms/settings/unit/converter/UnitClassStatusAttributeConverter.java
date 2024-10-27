package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import jakarta.persistence.AttributeConverter;


public class UnitClassStatusAttributeConverter implements AttributeConverter<UnitClassStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UnitClassStatus attribute) {
        return attribute.ordinal();
    }

    @Override
    public UnitClassStatus convertToEntityAttribute(Integer dbData) {
        return UnitClassStatus.getByOrdinal(dbData);
    }
}
