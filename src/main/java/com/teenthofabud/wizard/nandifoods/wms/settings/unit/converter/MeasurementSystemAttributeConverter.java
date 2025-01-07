package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import jakarta.persistence.AttributeConverter;


public class MeasurementSystemAttributeConverter implements AttributeConverter<MeasurementSystem, String> {

    @Override
    public String convertToDatabaseColumn(MeasurementSystem attribute) {
        return attribute.name();
    }

    @Override
    public MeasurementSystem convertToEntityAttribute(String dbData) {
        return MeasurementSystem.valueOf(dbData);
    }
}
