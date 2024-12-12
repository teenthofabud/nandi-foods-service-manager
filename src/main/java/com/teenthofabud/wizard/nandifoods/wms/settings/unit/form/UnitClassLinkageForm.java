package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "id value is required")
    @Pattern(regexp = "U(100[1-9]|10[1-9][0-9]|1[1-9][0-9]{2}|[2-4][0-9]{3})", message = "id value is invalid")
    protected String code;

}
