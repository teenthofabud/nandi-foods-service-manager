package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassForm;
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

    @JsonProperty("id")
    @NotNull(message = "id value is required")
    @Pattern(regexp = "U(100[1-9]|10[1-9][0-9]|1[1-9][0-9]{2}|[2-4][0-9]{3})", message = "id value is invalid")
    protected String code;

    @NotBlank(message = "bulk code value is required")
    private String bulkCode;

    @NotNull(message = "inventory UOM is required")
    private Boolean isInventory;

    @NotNull(message = "purchase UOM is required")
    private Boolean isPurchase;

    @NotNull(message = "sales UOM is required")
    private Boolean isSales;

    @NotNull(message = "production UOM is required")
    private Boolean isProduction;

    @Builder.Default
    private Optional<@Size(min = 1, message = "At least 1 UOM must be linked") List<@Valid UnitClassSelfLinkageForm>> linkedUOMs = Optional.empty();

    @Builder.Default
    private Optional<@Size(min = 1, message = "At least 1 PU/HU must be linked") List<@Valid UnitClassCrossLinkageForm>> linkedPUHUs = Optional.empty();

}
