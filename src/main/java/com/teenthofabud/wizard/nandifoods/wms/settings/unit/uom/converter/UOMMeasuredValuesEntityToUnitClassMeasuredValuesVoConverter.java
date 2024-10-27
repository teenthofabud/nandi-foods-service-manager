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
                .heightValue(source.getHeightValue())
                .lengthValue(source.getLengthValue())
                .volumeValue(source.getVolumeValue())
                .widthValue(source.getWidthValue())
                .weightValue(source.getWeightValue())
                .lengthUnit(source.getLengthUnit().toString())
                .heightUnit(source.getHeightUnit().toString())
                .widthUnit(source.getWidthUnit().toString())
                .volumeUnit(source.getVolumeUnit().toString())
                .weightUnit(source.getWeightUnit().toString())
                // id is discarded because metric and imperial have their own dedicated field in the front end vs shared instance on the backend
                .build();
        return target;
    }
}
