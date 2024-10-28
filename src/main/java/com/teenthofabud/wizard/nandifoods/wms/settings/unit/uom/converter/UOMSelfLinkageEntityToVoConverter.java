package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMSelfLinkageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UOMSelfLinkageEntityToVoConverter implements Converter<UOMSelfLinkageEntity, UOMSelfLinkageVo> {

    private UOMEntityToVoConverter uomEntityToVoConverter;

    @Autowired
    public UOMSelfLinkageEntityToVoConverter(UOMEntityToVoConverter uomEntityToVoConverter) {
        this.uomEntityToVoConverter = uomEntityToVoConverter;
    }

    @Override
    public UOMSelfLinkageVo convert(UOMSelfLinkageEntity source) {
        UOMSelfLinkageVo target = UOMSelfLinkageVo.builder()
                .to(uomEntityToVoConverter.convert(source.getToUOM()))
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
