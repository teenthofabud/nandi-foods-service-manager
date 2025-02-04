package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UOMEntityToVoConverter implements Converter<UOMEntity, UOMVo> {

    private UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter;

    @Autowired
    public UOMEntityToVoConverter(UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter) {
        this.uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter = uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter;
    }

    @Override
    public UOMVo convert(UOMEntity source) {
        List<UnitClassMeasuredValuesVo> measuredValues = source.getMeasuredValues().stream().map(f -> uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter.convert(f)).collect(Collectors.toList());
        UOMVo target = UOMVo.builder()
                ._class(source.get_class())
                .type(source.getType())
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .level(source.getLevel())
                .description(source.getDescription())
                .bulkCode(source.getBulkCode())
                .dateCreated(source.getAudit().getCreationTime().toLocalDate())
                .effectiveDate(source.getEffectiveDate())
                .modifiedDate(Optional.ofNullable(Optional.ofNullable(source.getAudit().getModificationTime()).map(f -> f.toLocalDate()).orElse(null)))
                .updatedBy(source.getAudit().getModifiedBy())
                .isInventory(source.getIsInventory())
                .isProduction(source.getIsProduction())
                .isSales(source.getIsSales())
                .isPurchase(source.getIsPurchase())
                .id(source.getId())
                .status(source.getStatus())
                .code(source.getCode())
                .measuredValues(measuredValues)
                .build();
        return target;
    }
}
