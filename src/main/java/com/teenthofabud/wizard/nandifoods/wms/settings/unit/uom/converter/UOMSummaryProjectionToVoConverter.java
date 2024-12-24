package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.projections.UOMSummaryProjection;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMSummaryVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UOMSummaryProjectionToVoConverter implements Converter<UOMSummaryProjection, UOMSummaryVo> {

    @Override
    public UOMSummaryVo convert(UOMSummaryProjection source) {
        UOMSummaryVo target = UOMSummaryVo.builder()
                .type(source.getLevelType().getType())
                .description(source.getDescription())
                .code(source.getCode())
                .bulkCode(source.getBulkCode())
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .metricWeightValue(source.getUomMeasuredValue().getWeightValue())
                .build();
        return target;
    }
}
