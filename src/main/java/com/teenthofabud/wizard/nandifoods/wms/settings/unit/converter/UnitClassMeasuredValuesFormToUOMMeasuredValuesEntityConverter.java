package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter implements Converter<UnitClassMeasuredValuesForm, UOMMeasuredValuesEntity> {

    @Override
    public UOMMeasuredValuesEntity convert(UnitClassMeasuredValuesForm source) {
        UOMMeasuredValuesEntity target = UOMMeasuredValuesEntity.builder()
                //.audit() -> automatically set by JPA for first time creation wrt creation related attributes
                .heightValue(source.getHeightValue())
                .lengthValue(source.getLengthValue())
                .measurementSystem(source.getMeasurementSystem())
                .volumeValue(source.getVolumeValue())
                .widthValue(source.getWidthValue())
                .weightValue(source.getWeightValue())
                .lengthUnit(source.getMeasurementSystem().getLengthUnit())
                .heightUnit(source.getMeasurementSystem().getHeightUnit())
                .widthUnit(source.getMeasurementSystem().getWidthUnit())
                .volumeUnit(source.getMeasurementSystem().getVolumeUnit())
                .weightUnit(source.getMeasurementSystem().getWeightUnit())
                .build();
        return target;
    }
}
