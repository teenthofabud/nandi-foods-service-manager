package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMSelfLinkageForm;
import org.springframework.stereotype.Component;

@Component
public class UOMSelfLinkageEntityReducer {
    public UOMSelfLinkageEntity reduce(UOMSelfLinkageForm source, UOMEntity from, UOMEntity to) {
        UOMSelfLinkageEntity target = UOMSelfLinkageEntity.builder()
                .fromUOM(from)
                .toUOM(to)
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
