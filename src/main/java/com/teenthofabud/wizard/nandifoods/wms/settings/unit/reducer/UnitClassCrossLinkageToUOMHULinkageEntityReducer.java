package com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.HUEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.UOMHULinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import org.springframework.stereotype.Component;

@Component
public class UnitClassCrossLinkageToUOMHULinkageEntityReducer {
    public UOMHULinkageEntity reduce(UnitClassCrossLinkageForm source, UOMEntity uom, HUEntity hu) {
        UOMHULinkageEntity target = UOMHULinkageEntity.builder()
                .hu(hu)
                .uom(uom)
                .maximumQuantity(source.getMaximumQuantity())
                .minimumQuantity(source.getMinimumQuantity())
                .build();
        return target;
    }
}
