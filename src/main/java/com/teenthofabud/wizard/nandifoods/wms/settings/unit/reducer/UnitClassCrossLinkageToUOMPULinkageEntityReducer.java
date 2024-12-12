package com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.PUEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.UOMPULinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import org.springframework.stereotype.Component;

@Component
public class UnitClassCrossLinkageToUOMPULinkageEntityReducer {
    public UOMPULinkageEntity reduce(UnitClassCrossLinkageForm source, UOMEntity uom, PUEntity pu) {
        UOMPULinkageEntity target = UOMPULinkageEntity.builder()
                .pu(pu)
                .uom(uom)
                .maximumQuantity(source.getMaximumQuantity())
                .minimumQuantity(source.getMinimumQuantity())
                .build();
        return target;
    }
}
