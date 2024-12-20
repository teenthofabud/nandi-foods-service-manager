package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.EnumValidator;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitClassMeasuredValuesForm {

    @EnumValidator(enumClazz = MetricSystem.class, message = "Metric system is invalid")
    protected String metricSystem;

    @NotNull(message = "length is required")
    @DecimalMin(value = "0.1", message = "length cannot be zero or less")
    private Double lengthValue;

    @NotNull(message = "width is required")
    @DecimalMin(value = "0.1", message = "width cannot be zero or less")
    private Double widthValue;

    @NotNull(message = "height is required")
    @DecimalMin(value = "0.1", message = "height cannot be zero or less")
    private Double heightValue;

    @NotNull(message = "volume is required")
    @DecimalMin(value = "0.1", message = "volume cannot be zero or less")
    private Double volumeValue;

    @NotNull(message = "weight is required")
    @DecimalMin(value = "0.1", message = "weight cannot be zero or less")
    private Double weightValue;

}
