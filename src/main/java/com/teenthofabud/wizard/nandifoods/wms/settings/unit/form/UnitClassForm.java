package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassForm {

    @EnumValidator(enumClazz = UnitClassType.class, message = "Class type is invalid")
    protected String type;


    @EnumValidator(enumClazz = UnitClassLevelType.class, message = "Level type is invalid")
    protected String level;

    @NotBlank(message = "name is required")
    protected String name;

    @NotNull
    @NotBlank(message = "description is required")
    @Length(min = 1, message = "description should be at least 1 character long")
    protected String description;

    @NotBlank(message = "long name is required")
    protected String longName;

    @NotBlank(message = "short name is required")
    protected String shortName;

    @Valid
    protected UnitClassMeasuredValuesForm metric;

    @Valid
    protected UnitClassMeasuredValuesForm imperial;

}
