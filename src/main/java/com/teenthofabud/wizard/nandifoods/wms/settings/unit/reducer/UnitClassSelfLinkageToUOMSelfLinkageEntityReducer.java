package com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import org.springframework.stereotype.Component;

@Component
public class UnitClassSelfLinkageToUOMSelfLinkageEntityReducer {
    public UOMSelfLinkageEntity reduce(UnitClassSelfLinkageForm source, UOMEntity from, UOMEntity to) {
        UOMSelfLinkageEntity target = UOMSelfLinkageEntity.builder()
                .fromUom(from)
                .toUom(to)
                .quantity(source.getQuantity())
                .build();
        return target;
    }
}
