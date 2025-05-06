package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;

/*
 * Used when applying a JSON Patch to a DTO and selectively updating only specific fields
 * on the entity, without overwriting the entire object. Helps maintain separation of
 * concerns between patching logic and persistence logic.
 * Follows SOLID principles by isolating patch logic (SRP) and allowing flexible extension (OCP).
 */

public interface UOMDtoV2toUOMEntityPatcher {
    public UOMEntity scalerPatcher(UOMDtoV2 dto, UOMEntity entity);
}
