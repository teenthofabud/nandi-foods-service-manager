package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

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
public class UnitClassSelfLinkageForm extends UnitClassLinkageForm {

    @NotNull(message = "quantity value is required")
    @Min(value = 1, message = "minimum quantity value is 1")
    protected Integer quantity;

}
