package com.teenthofabud.wizard.nandifoods.wms.settings.constants;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum RightsType {

    @FieldNameConstants.Include VIEW_GLOBAL,
    @FieldNameConstants.Include CREATE_EDIT_GLOBAL;
}
