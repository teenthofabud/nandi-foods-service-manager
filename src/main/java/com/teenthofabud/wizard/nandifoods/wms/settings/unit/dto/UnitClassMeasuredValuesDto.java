package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UnitClassMeasuredValuesDto {

    @NotNull
    @DecimalMin("0.1")
    @Builder.Default
    private Double lengthValue = 0.0d;

    @NotNull
    @DecimalMin("0.1")
    @Builder.Default
    private Double widthValue = 0.0d;

    @NotNull
    @DecimalMin("0.1")
    @Builder.Default
    private Double heightValue = 0.0d;

    @NotNull
    @DecimalMin("0.1")
    @Builder.Default
    private Double volumeValue = 0.0d;

    @NotNull
    @DecimalMin("0.1")
    @Builder.Default
    private Double weightValue = 0.0d;

}
