package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UnitClassMeasuredValuesDtoCollection {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified")
    protected Set<@Valid UnitClassMeasuredValuesDto> measuredValues
            //= Optional.of(new HashSet<>(Collections.nCopies(2, UnitClassMeasuredValuesDto.builder().build())));
            = new TreeSet<UnitClassMeasuredValuesDto>(
                    Set.of(
                            UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MeasurementSystem.SI.name())).build(),
                            UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MeasurementSystem.IMPERIAL.name())).build()
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
