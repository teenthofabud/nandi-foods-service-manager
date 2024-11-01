package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UnitClassMeasuredValuesDto {

    private Optional<@DecimalMin(value = "0.1", message = "length cannot be zero or less") Double> lengthValue;

    private Optional<@DecimalMin(value = "0.1", message = "width cannot be zero or less") Double>  widthValue;

    private Optional<@DecimalMin(value = "0.1", message = "height cannot be zero or less") Double>  heightValue;

    private Optional<@DecimalMin(value = "0.1", message = "volume cannot be zero or less") Double>  volumeValue;

    private Optional<@DecimalMin(value = "0.1", message = "weight cannot be zero or less") Double>  weightValue;

    public UnitClassMeasuredValuesDto() {
        this.lengthValue = Optional.empty();
        this.widthValue = Optional.empty();
        this.heightValue = Optional.empty();
        this.volumeValue = Optional.empty();
        this.weightValue = Optional.empty();
    }
}
