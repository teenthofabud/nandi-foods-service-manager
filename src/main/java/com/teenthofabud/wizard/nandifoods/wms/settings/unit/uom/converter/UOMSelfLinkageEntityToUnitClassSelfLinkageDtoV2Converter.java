package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter implements Converter<UOMSelfLinkageEntity, UnitClassSelfLinkageDtoV2> {

    @Override
    public UnitClassSelfLinkageDtoV2 convert(UOMSelfLinkageEntity source) {
        UnitClassSelfLinkageDtoV2 target = UnitClassSelfLinkageDtoV2.builder()
                .code(source.getToUom().getCode())
                .fromCode(source.getFromUom().getCode())
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
