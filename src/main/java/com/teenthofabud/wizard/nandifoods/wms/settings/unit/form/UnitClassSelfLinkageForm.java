package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassSelfLinkageContract;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UnitClassSelfLinkageForm extends UnitClassLinkageForm implements UnitClassSelfLinkageContract {

    @Min(value = 1, message = "minimum quantity value is 1")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "number of UOMs linked to")
    protected Integer quantity;

}
