package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassForm;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMForm extends UnitClassForm {

    // Not editable in edit workflow
    @JsonIgnore
    private String bulkCode;

    @NotNull(message = "inventory UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isInventory;

    @NotNull(message = "purchase UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isPurchase;

    @NotNull(message = "sales UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSales;

    @NotNull(message = "production UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isProduction;

    @Builder.Default
    @ArraySchema(schema = @Schema(
            implementation = UnitClassSelfLinkageForm.class,
            description = "this UOM is linked to these UOMs"))
    private Optional<@Size(min = 1, message = "At least 1 UOM must be linked") List<@Valid UnitClassSelfLinkageForm>> linkedUOMs = Optional.empty();

    @Builder.Default
    private Optional<@Size(min = 1, message = "At least 1 PU/HU must be linked") List<@Valid UnitClassCrossLinkageForm>> linkedPUHUs = Optional.empty();

}
