package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OptionalUnitClassMeasuredValuesDtoCollection {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified") Set<@Valid UnitClassMeasuredValuesDto>> measuredValues
            //= Optional.of(new HashSet<>(Collections.nCopies(2, UnitClassMeasuredValuesDto.builder().build())));
            = Optional.of(
                    Set.of(
                            UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MetricSystem.SI.name())).build(),
                            UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MetricSystem.IMPERIAL.name())).build()
                    )
    );

    /*public UnitClassMeasuredValuesDto() {
        this.lengthValue = Optional.empty();
        this.widthValue = Optional.empty();
        this.heightValue = Optional.empty();
        this.volumeValue = Optional.empty();
        this.weightValue = Optional.empty();
    }*/
}
