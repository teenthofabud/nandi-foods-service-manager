package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.CrossLinkageUnitClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UnitClassCrossLinkageForm extends UnitClassLinkageForm {

    @CrossLinkageUnitClassType(anyOf = { UnitClassType.PU, UnitClassType.HU })
    protected UnitClassType type;

    @NotNull(message = "minimum quantity value is required")
    @Min(value = 1, message = "minimum value for minimum quantity is 1")
    protected Integer minimumQuantity;

    @NotNull(message = "maximum quantity value is required")
    @Min(value = 1, message = "minimum value for maximum quantity is 1")
    protected Integer maximumQuantity;

}
