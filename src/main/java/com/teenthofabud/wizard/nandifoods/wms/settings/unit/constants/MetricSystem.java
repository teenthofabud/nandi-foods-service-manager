package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
public enum MetricSystem {

    @FieldNameConstants.Include SI,
    @FieldNameConstants.Include IMPERIAL;

}
