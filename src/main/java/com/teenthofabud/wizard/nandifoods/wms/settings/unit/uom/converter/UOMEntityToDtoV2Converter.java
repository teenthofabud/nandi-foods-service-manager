package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UOMEntityToDtoV2Converter implements Converter<UOMEntity, UOMDtoV2> {

    private DateTimeFormatter approvalTimeFormat;
    private DateTimeFormatter modificationTimeFormat;
    private DateTimeFormatter creationTimeFormat;
    private UOMMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter;
    private UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter;

    public UOMEntityToDtoV2Converter(@Value("${wms.settings.unit.approvalTimeFormat}") String approvalTimeFormat,
                                     @Value("${wms.settings.unit.modificationTimeFormat}") String modificationTimeFormat,
                                     @Value("${wms.settings.unit.creationTimeFormat}") String creationTimeFormat,
                                     UOMMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter,
                                     UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter) {
        this.approvalTimeFormat = DateTimeFormatter.ofPattern(approvalTimeFormat);
        this.modificationTimeFormat = DateTimeFormatter.ofPattern(modificationTimeFormat);
        this.creationTimeFormat = DateTimeFormatter.ofPattern(creationTimeFormat);
        this.uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter = uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter;
        this.uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter = uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter;
    }

    @Override
    public UOMDtoV2 convert(UOMEntity source) {
        /*Set<UnitClassMeasuredValuesDtoV2> measuredValues = source.getUomMeasuredValues().stream()
                .map(f -> uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter.convert(f))
                .collect(Collectors.toSet());*/
        List<UnitClassMeasuredValuesDtoV2> measuredValues = source.getMeasuredValues().stream().map(f -> uomMeasuredValuesEntityToUnitClassMeasuredValuesDtoV2Converter.convert(f)).collect(Collectors.toList());
        List<UnitClassSelfLinkageDtoV2> linkedUOMs = source.getFromUOMs().stream().map(f -> uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter.convert(f)).collect(Collectors.toList());
        UOMDtoV2 target = UOMDtoV2.builder()
                .type(source.getType())
                .shortName(source.getShortName())
                .level(source.getLevel())
                .description(source.getDescription())
                .effectiveDate(source.getEffectiveDate())
                .isInventory(source.getIsInventory())
                .isProduction(source.getIsProduction())
                .isSales(source.getIsSales())
                .isPurchase(source.getIsPurchase())
                .status(source.getStatus())
                .measuredValues(measuredValues)
                .linkedUOMs(linkedUOMs)
                .code(source.getCode())
                .longName(source.getLongName())
                .build();

        return target;
    }
}
