package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.MeasuredValuesContract;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitClassMeasuredValuesForm implements Comparable<UnitClassMeasuredValuesForm>, MeasuredValuesContract {

    //@EnumValidator(enumClazz = MeasurementSystem.class, message = "Measurement system is invalid")
    @NotNull(message = "measurement system name is required")
    @EqualsAndHashCode.Include
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Measurement system represented by this values set")
    protected MeasurementSystem measurementSystem;

    @NotNull(message = "length is required")
    @DecimalMin(value = "0.1", message = "length cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Length of this UOM up to 2 decimal places")
    private Double lengthValue;

    @NotNull(message = "width is required")
    @DecimalMin(value = "0.1", message = "width cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Width of this UOM up to 2 decimal places")
    private Double widthValue;

    @NotNull(message = "height is required")
    @DecimalMin(value = "0.1", message = "height cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Height of this UOM up to 2 decimal places")
    private Double heightValue;

    @NotNull(message = "volume is required")
    @DecimalMin(value = "0.1", message = "volume cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Volume of this UOM up to 2 decimal places")
    private Double volumeValue;

    @NotNull(message = "weight is required")
    @DecimalMin(value = "0.1", message = "weight cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Weight of this UOM up to 2 decimal places")
    private Double weightValue;

    @Override
    public int compareTo(UnitClassMeasuredValuesForm o) {
        return this.getMeasurementSystem().compareTo(o.getMeasurementSystem());
    }
}
