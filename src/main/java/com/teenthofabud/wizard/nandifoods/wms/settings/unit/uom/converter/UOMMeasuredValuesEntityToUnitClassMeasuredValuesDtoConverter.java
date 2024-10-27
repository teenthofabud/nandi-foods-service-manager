package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UOMMeasuredValuesEntityToUnitClassMeasuredValuesDtoConverter implements Converter<UOMMeasuredValuesEntity, UnitClassMeasuredValuesDto> {

    @Override
    public UnitClassMeasuredValuesDto convert(UOMMeasuredValuesEntity source) {
        UnitClassMeasuredValuesDto target = UnitClassMeasuredValuesDto.builder()
                //.audit() -> automatically set by JPA for first time creation wrt creation related attributes
                .heightValue(source.getHeightValue())
                .lengthValue(source.getLengthValue())
                .volumeValue(source.getVolumeValue())
                .widthValue(source.getWidthValue())
                .weightValue(source.getWeightValue())
                // units are discarded because they are controlled directly in backend
                // id is discarded because metric and imperial have their own dedicated field in the front end vs shared instance on the backend
                .build();
        return target;
    }
}
