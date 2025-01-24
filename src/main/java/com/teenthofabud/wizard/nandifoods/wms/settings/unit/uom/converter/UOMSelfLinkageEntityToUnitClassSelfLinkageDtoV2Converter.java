package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter implements Converter<UOMSelfLinkageEntity, UnitClassSelfLinkageDtoV2> {

    private UOMEntityToDtoV2Converter uomEntityToDtoV2Converter;

    @Autowired
    public UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter(UOMEntityToDtoV2Converter uomEntityToDtoV2Converter) {
        this.uomEntityToDtoV2Converter = uomEntityToDtoV2Converter;
    }

    @Override
    public UnitClassSelfLinkageDtoV2 convert(UOMSelfLinkageEntity source) {
        UnitClassSelfLinkageDtoV2 target = UnitClassSelfLinkageDtoV2.builder()
                .code(uomEntityToDtoV2Converter.convert(source.getToUom()).getCode())
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
