package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
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

    private Boolean isInventory;

    private Boolean isPurchase;

    private Boolean isSales;

    private Boolean isProduction;

    @Builder.Default
    private Optional<@Size(min = 1, message = "At least 1 UOM must be linked") List<UnitClassSelfLinkageForm>> linkedUOMs = Optional.empty();

    @Builder.Default
    private Optional<@Size(min = 1, message = "At least 1 PU/HU must be linked") List<UnitClassCrossLinkageForm>> linkedPUHUs = Optional.empty();



}
