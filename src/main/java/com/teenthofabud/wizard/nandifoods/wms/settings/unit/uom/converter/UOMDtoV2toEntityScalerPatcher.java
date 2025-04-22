package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
@Component
public class UOMDtoV2toEntityScalerPatcher implements UOMDtoV2toUOMEntityPatcher{

    @Override
    public UOMEntity scalerPatcher(UOMDtoV2 patchUOM, UOMEntity uomEntity) {
        if (patchUOM.getIsInventory()!=null){
            uomEntity.setIsInventory(patchUOM.getIsInventory());
        }
        if (patchUOM.getIsPurchase()!=null){
            uomEntity.setIsPurchase(patchUOM.getIsPurchase());
        }
        if (patchUOM.getIsSales()!=null){
            uomEntity.setIsSales(patchUOM.getIsSales());
        }
        if (patchUOM.getIsProduction()!=null){
            uomEntity.setIsProduction(patchUOM.getIsProduction());
        }
        return uomEntity;
    }
}
