package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassForm;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class HUForm extends UnitClassForm {

    @JsonProperty("id")
    @Pattern(regexp = "U(500[1-9]|50[1-9][0-9]|5[1-9][0-9]{2}|[6-9][0-9]{3})", message = "code value is invalid")
    protected String code;

    private Boolean isFlexHu;

}
