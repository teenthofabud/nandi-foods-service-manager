package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import jakarta.persistence.AttributeConverter;


public class MetricSystemAttributeConverter implements AttributeConverter<MetricSystem, String> {

    @Override
    public String convertToDatabaseColumn(MetricSystem attribute) {
        return attribute.name();
    }

    @Override
    public MetricSystem convertToEntityAttribute(String dbData) {
        return MetricSystem.valueOf(dbData);
    }
}
