package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.Id;

@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UnitClassMeasuredValuesDtoV2 implements Comparable<UnitClassMeasuredValuesDtoV2> {

    @JsonSetter(nulls = Nulls.SKIP)
    @NotNull(message = "Measurement system can't be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Measurement system represented by this values set")
    protected MeasurementSystem measurementSystem;

    @JsonSetter(nulls = Nulls.SKIP)
    @NotNull(message = "id can't be null")
    @Schema(example = "1", description = "Measurement system primary key")
    @Id
    private Long id;

    @JsonSetter(nulls = Nulls.SKIP)
    @DecimalMin(value = "0.1", message = "length cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Length of this UOM up to 2 decimal places")
    private Double length;

    @JsonSetter(nulls = Nulls.SKIP)
    @DecimalMin(value = "0.1", message = "width cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Width of this UOM up to 2 decimal places")
    private Double width;

    @JsonSetter(nulls = Nulls.SKIP)
    @DecimalMin(value = "0.1", message = "height cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Height of this UOM up to 2 decimal places")
    private Double height;

    @JsonSetter(nulls = Nulls.SKIP)
    @DecimalMin(value = "0.1", message = "volume cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Volume of this UOM up to 2 decimal places")
    private Double volume;

    @JsonSetter(nulls = Nulls.SKIP)
    @DecimalMin(value = "0.1", message = "weight cannot be zero or less")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Weight of this UOM up to 2 decimal places")
    private Double weight;

    @Override
    public int compareTo(UnitClassMeasuredValuesDtoV2 o) {
        return this.measurementSystem.compareTo(o.getMeasurementSystem());
    }
}
