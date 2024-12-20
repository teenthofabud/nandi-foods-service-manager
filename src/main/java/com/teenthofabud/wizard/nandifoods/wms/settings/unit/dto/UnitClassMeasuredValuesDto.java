package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.validator.OptionalEnumValidator;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UnitClassMeasuredValuesDto {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @OptionalEnumValidator(enumClazz = MetricSystem.class, message = "Metric system is invalid")
    protected Optional<String> metricSystem = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<@DecimalMin(value = "0.1", message = "length cannot be zero or less") Double> lengthValue = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<@DecimalMin(value = "0.1", message = "width cannot be zero or less") Double>  widthValue = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<@DecimalMin(value = "0.1", message = "height cannot be zero or less") Double>  heightValue = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<@DecimalMin(value = "0.1", message = "volume cannot be zero or less") Double>  volumeValue = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<@DecimalMin(value = "0.1", message = "weight cannot be zero or less") Double>  weightValue = Optional.empty();

    /*public UnitClassMeasuredValuesDto() {
        this.lengthValue = Optional.empty();
        this.widthValue = Optional.empty();
        this.heightValue = Optional.empty();
        this.volumeValue = Optional.empty();
        this.weightValue = Optional.empty();
    }*/
}
