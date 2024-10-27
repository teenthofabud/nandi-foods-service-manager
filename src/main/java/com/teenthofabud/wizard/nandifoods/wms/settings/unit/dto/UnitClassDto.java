package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassDto {

    @EnumValidator(enumClazz = UnitClassType.class, message = "Class type is invalid")
    @Builder.Default
    protected String type = "";

    @EnumValidator(enumClazz = UnitClassLevelType.class, message = "Level type is invalid")
    @Builder.Default
    protected String level = "";

    @NotBlank
    @Builder.Default
    protected String name = "";

    @Builder.Default
    protected String code = "";

    @NotBlank
    @Builder.Default
    protected String description = "";

    @NotBlank
    @Builder.Default
    protected String longName = "";

    @NotBlank
    @Builder.Default
    protected String shortName = "";

    @NotBlank
    @Builder.Default
    protected String status = "";

    @Valid
    @Builder.Default
    protected UnitClassMeasuredValuesDto metric = new UnitClassMeasuredValuesDto();

    @Valid
    @Builder.Default
    protected UnitClassMeasuredValuesDto imperial = new UnitClassMeasuredValuesDto();

}
