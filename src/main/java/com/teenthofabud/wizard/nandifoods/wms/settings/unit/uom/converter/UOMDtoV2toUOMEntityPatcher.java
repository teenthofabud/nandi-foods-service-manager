package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;

public interface UOMDtoV2toUOMEntityPatcher {
    public UOMEntity scalerPatcher(UOMDtoV2 dto, UOMEntity entity);
}
