package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassForm {


    @EnumValidator(enumClazz = UnitClassLevelType.class, message = "Level is invalid")
    protected String level;

    @EnumValidator(enumClazz = UnitClassLevelType.class, message = "Type is invalid")
    protected String type;

    @NotBlank(message = "name is required")
    protected String name;

    //@NotBlank(message = "description is required")
    //@Length(min = 1, message = "description should be at least 1 character long")
    protected String description;

    //@NotBlank(message = "long name is required")
    protected String longName;

    //@NotBlank(message = "short name is required")
    protected String shortName;

    @Size(min = 2, max = 2, message = "Both of imperial and metric measured values must be specified")
    protected List<@Valid UnitClassMeasuredValuesForm> measuredValues;

/*    @Valid
    protected UnitClassMeasuredValuesForm metric;

    @Valid
    protected UnitClassMeasuredValuesForm imperial;*/

}
