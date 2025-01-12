package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UOMMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter implements Converter<UOMMeasuredValuesEntity, UnitClassMeasuredValuesDtoV2> {

    @Override
    public UnitClassMeasuredValuesDtoV2 convert(UOMMeasuredValuesEntity source) {
        UnitClassMeasuredValuesDtoV2 target = UnitClassMeasuredValuesDtoV2.builder()
                //.audit() -> automatically set by JPA for first time creation wrt creation related attributes
                .measurementSystem(source.getMeasurementSystem())
                .height(source.getHeight())
                .length(source.getLength())
                .volume(source.getVolume())
                .width(source.getWidth())
                .weight(source.getWeight())
                .id(source.getId()) // id is mapped because measured values type are managed in a collection in the front end model and it is difficult to manage them within a shared collection if id si not available
                // NO LONGER VALID: id is discarded because metric and imperial have their own dedicated field in the front end vs shared instance on the backend
                .build();
        return target;
    }
}
