package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class UOMFormToEntityConverter implements Converter<UOMForm, UOMEntity> {

    @Override
    public UOMEntity convert(UOMForm source) {
        String longName = StringUtils.hasText(source.getLongName()) ? source.getLongName()
                : String.format("%s %s (%s)", source.getCode(), source.getType(), source.getDescription());
        String shortName = StringUtils.hasText(source.getShortName()) ? source.getShortName()
                : String.format("%s (%s)", source.getType(), source.getCode());
        UOMEntity target = UOMEntity.builder()
                .code(source.getCode())
                .type(UnitClassType.UOM)
                .description(source.getDescription())
                .longName(longName)
                .shortName(shortName)
                .levelType(UnitClassLevelType.getByLevel(source.getLevel()))
                .bulkCode(source.getBulkCode())
                .status(UnitClassStatus.PENDING)
                .isInventory(source.getIsInventory())
                .isProduction(source.getIsProduction())
                .isSales(source.getIsSales())
                .isPurchase(source.getIsPurchase())
                /*.fromUOMs(new ArrayList<>())
                .toUOMs(new ArrayList<>())
                .puLinks(new ArrayList<>())
                .huLinks(new ArrayList<>())
                .uomMeasuredValues(new ArrayList<>())*/
                .effectiveDate(source.getEffectiveDate())
                .build();
        // audit is set automatically for creation related attributes
        return target;
    }
}
