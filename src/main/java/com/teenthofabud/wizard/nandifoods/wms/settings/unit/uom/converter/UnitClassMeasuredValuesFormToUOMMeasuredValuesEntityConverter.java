package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.MetricSystemContext;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import systems.uom.common.USCustomary;

import javax.measure.MetricPrefix;

import static tech.units.indriya.unit.Units.*;

@Component
public class UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter implements Converter<UnitClassMeasuredValuesForm, UOMMeasuredValuesEntity> {

    @Override
    public UOMMeasuredValuesEntity convert(UnitClassMeasuredValuesForm source) {
        UOMMeasuredValuesEntity target = UOMMeasuredValuesEntity.builder()
                //.audit() -> automatically set by JPA for first time creation wrt creation related attributes
                .heightValue(source.getHeightValue())
                .lengthValue(source.getLengthValue())
                .metricSystem(MetricSystemContext.get())
                .volumeValue(source.getVolumeValue())
                .widthValue(source.getWidthValue())
                .weightValue(source.getWeightValue())
                .lengthUnit(MetricSystemContext.get().compareTo(MetricSystem.SI) == 0 ?
                        MetricPrefix.CENTI(METRE) : USCustomary.INCH)
                .heightUnit(MetricSystemContext.get().compareTo(MetricSystem.SI) == 0 ?
                        MetricPrefix.CENTI(METRE) : USCustomary.INCH)
                .widthUnit(MetricSystemContext.get().compareTo(MetricSystem.SI) == 0 ?
                        MetricPrefix.CENTI(METRE) : USCustomary.INCH)
                .volumeUnit(MetricSystemContext.get().compareTo(MetricSystem.SI) == 0 ?
                        CUBIC_METRE : USCustomary.CUBIC_FOOT)
                .weightUnit(MetricSystemContext.get().compareTo(MetricSystem.SI) == 0 ?
                        MetricPrefix.KILO(GRAM) : USCustomary.POUND)
                .build();
        return target;
    }
}
