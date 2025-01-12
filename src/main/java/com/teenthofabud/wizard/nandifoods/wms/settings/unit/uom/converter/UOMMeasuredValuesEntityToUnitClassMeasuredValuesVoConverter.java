package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter implements Converter<UOMMeasuredValuesEntity, UnitClassMeasuredValuesVo> {

    @Override
    public UnitClassMeasuredValuesVo convert(UOMMeasuredValuesEntity source) {
        UnitClassMeasuredValuesVo target = UnitClassMeasuredValuesVo.builder()
                //.audit() -> automatically set by JPA for first time creation wrt creation related attributes
                .measurementSystem(source.getMeasurementSystem())
                .heightValue(source.getHeight())
                .lengthValue(source.getLength())
                .volumeValue(source.getVolume())
                .widthValue(source.getWidth())
                .weightValue(source.getWeight())
                .lengthUnit(source.getLengthUnit().toString())
                .heightUnit(source.getHeightUnit().toString())
                .widthUnit(source.getWidthUnit().toString())
                .volumeUnit(source.getVolumeUnit().toString())
                .weightUnit(source.getWeightUnit().toString())
                .id(source.getId()) // id is mapped because measured values type are managed in a collection in the front end model and it is difficult to manage them within a shared collection if id si not available
                // NO LONGER VALID: id is discarded because metric and imperial have their own dedicated field in the front end vs shared instance on the backend
                .build();
        return target;
    }
}
