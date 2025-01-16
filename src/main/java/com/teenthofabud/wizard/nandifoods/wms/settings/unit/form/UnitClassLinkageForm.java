package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassLinkageForm {

    @JsonProperty("id")
    @Pattern(regexp = "U(100[1-9]|10[1-9][0-9]|1[1-9][0-9]{2}|[2-4][0-9]{3})", message = "id value is invalid")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "UOM ID")
    protected String code;

}
