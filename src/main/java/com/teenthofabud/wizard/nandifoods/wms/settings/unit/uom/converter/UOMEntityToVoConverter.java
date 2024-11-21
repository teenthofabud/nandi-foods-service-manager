package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;

@Component
public class UOMEntityToVoConverter implements Converter<UOMEntity, UOMVo> {

    private DateTimeFormatter approvalTimeFormat;
    private DateTimeFormatter modificationTimeFormat;
    private DateTimeFormatter creationTimeFormat;
    private UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter;

    public UOMEntityToVoConverter(@Value("${inventory.settings.unit.approvalTimeFormat}") String approvalTimeFormat,
                                  @Value("${inventory.settings.unit.modificationTimeFormat}") String modificationTimeFormat,
                                  @Value("${inventory.settings.unit.creationTimeFormat}") String creationTimeFormat,
                                  UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter) {
        this.approvalTimeFormat = DateTimeFormatter.ofPattern(approvalTimeFormat);
        this.modificationTimeFormat = DateTimeFormatter.ofPattern(modificationTimeFormat);
        this.creationTimeFormat = DateTimeFormatter.ofPattern(creationTimeFormat);
        this.uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter = uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter;
    }

    @Override
    public UOMVo convert(UOMEntity source) {
        UOMVo target = UOMVo.builder()
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .level(source.getLevelType().getLevel())
                .description(source.getDescription())
                .bulkCode(source.getBulkCode())
                .dateCreated(ObjectUtils.isEmpty(source.getAudit().getCreationTime()) ?
                        "" : source.getAudit().getCreationTime().format(creationTimeFormat))
                .effectiveDate(ObjectUtils.isEmpty(source.getAudit().getApprovalTime()) ?
                        "" : source.getAudit().getApprovalTime().format(approvalTimeFormat))
                .modifiedDate(ObjectUtils.isEmpty(source.getAudit().getModificationTime()) ?
                        "" : source.getAudit().getModificationTime().format(modificationTimeFormat))
                .updatedBy(source.getAudit().getModifiedBy())
                .updatedBy(source.getAudit().getModifiedBy())
                .isInventory(source.getIsInventory())
                .isProduction(source.getIsProduction())
                .isSales(source.getIsSales())
                .isPurchase(source.getIsPurchase())
                .id(source.getId())
                .status(source.getStatus().name())
                .code(source.getCode())
                .imperial(
                        uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter.convert(
                                source.getUomMeasuredValues().stream().filter(f -> f.getMetricSystem().compareTo(MetricSystem.IMPERIAL) == 0).findFirst().get()))
                .metric(
                        uomMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter.convert(
                                source.getUomMeasuredValues().stream().filter(f -> f.getMetricSystem().compareTo(MetricSystem.SI) == 0).findFirst().get()))
                .build();
        return target;
    }
}
