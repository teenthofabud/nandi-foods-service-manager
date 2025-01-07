package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassSelfLinkageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter implements Converter<UOMSelfLinkageEntity, UnitClassSelfLinkageVo> {

    private UOMEntityToVoConverter uomEntityToVoConverter;

    @Autowired
    public UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter(UOMEntityToVoConverter uomEntityToVoConverter) {
        this.uomEntityToVoConverter = uomEntityToVoConverter;
    }

    @Override
    public UnitClassSelfLinkageVo convert(UOMSelfLinkageEntity source) {
        UnitClassSelfLinkageVo target = UnitClassSelfLinkageVo.builder()
                .to(uomEntityToVoConverter.convert(
                        UOMSelfLinkageContext.getCascadeLevelContext() ? source.getFromUom() : source.getToUom()))
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
