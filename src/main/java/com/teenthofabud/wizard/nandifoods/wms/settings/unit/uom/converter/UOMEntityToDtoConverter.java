package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UOMEntityToDtoConverter implements Converter<UOMEntity, UOMDto> {

    @Override
    public UOMDto convert(UOMEntity source) {
        UOMDto target = UOMDto.builder()
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .level(source.getLevelType().getLevel())
                .type(source.getLevelType().getType())
                .description(source.getDescription())
                .bulkCode(source.getBulkCode())
                .isInventory(source.getIsInventory())
                .isProduction(source.getIsProduction())
                .isSales(source.getIsSales())
                .isPurchase(source.getIsPurchase())
                .status(source.getStatus().name())
                .code(source.getCode())
                .build();
        return target;
    }
}
