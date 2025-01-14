package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassLevelContract;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import com.teenthofabud.wizard.nandifoods.wms.validator.OptionalEnumValidator;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.FirstOrder;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.SecondOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UnitClassLevelTypeValidator(mandatory = false)
@SuperBuilder
@Getter
@Setter
@GroupSequence({ UnitClassDto.class, FirstOrder.class, SecondOrder.class })
public abstract class UnitClassDto implements UnitClassLevelContract {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<UnitClassLevel> level = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @OptionalEnumValidator(enumClazz = UnitClassStatus.class, message = "Status is invalid")
    protected Optional<String> status = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("name")
    @Builder.Default
    protected Optional<UnitClassType> type = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> description = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> shortName = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@FutureOrPresent(groups = FirstOrder.class) @UntilDays(mandatory = false, count = 91, groups = SecondOrder.class) LocalDate> effectiveDate = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @JsonUnwrapped
    protected Optional<@Valid UnitClassMeasuredValuesDtoCollection> measuredValuesDtoCollection = Optional.of(
            UnitClassMeasuredValuesDtoCollection
                    .builder()
                    .measuredValues(new TreeSet<UnitClassMeasuredValuesDto>(
                            Set.of(
                                    UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MeasurementSystem.SI.name())).build(),
                                    UnitClassMeasuredValuesDto.builder().metricSystem(Optional.of(MeasurementSystem.IMPERIAL.name())).build()
                            )
                    ))
                    .build()
    );
    //protected OptionalUnitClassMeasuredValuesDtoCollection measuredValuesDtoCollection = OptionalUnitClassMeasuredValuesDtoCollection.builder().build();
    //protected Optional<@Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified") List<@Valid UnitClassMeasuredValuesDto>> measuredValues = Optional.of(new ArrayList<>());
    /*protected Optional<@Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified") Set<@Valid UnitClassMeasuredValuesDto>> measuredValues
            = Optional.of(
                    Set.of(
                            UnitClassMeasuredValuesDto.builder().measurementSystem(Optional.of(MeasurementSystem.SI.name())).build(),
                            UnitClassMeasuredValuesDto.builder().measurementSystem(Optional.of(MeasurementSystem.IMPERIAL.name())).build()
                    )
    );*/
    /*protected Optional<@Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified") Set<@Valid UnitClassMeasuredValuesDto>> measuredValues
            = Optional.of(new HashSet<>(Collections.nCopies(2, UnitClassMeasuredValuesDto.builder().build())));*/

    /*@JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Valid UnitClassMeasuredValuesDto> metric = Optional.of(new UnitClassMeasuredValuesDto());

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Valid UnitClassMeasuredValuesDto> imperial = Optional.of(new UnitClassMeasuredValuesDto());*/

    /*public UnitClassDto() {
        this.level = Optional.empty();
        this.name = Optional.empty();
        this.description = Optional.empty();
        this.longName = Optional.empty();
        this.shortName = Optional.empty();
        this.status = Optional.empty();
        this.metric = Optional.of(new UnitClassMeasuredValuesDto());
        this.imperial = Optional.of(new UnitClassMeasuredValuesDto());
    }*/

    @Override
    public UnitClassLevel getLevel() {
        return this.level.isPresent() ? this.level.get() : null;
    }

    @Override
    public UnitClassType getType() {
        return this.type.isPresent() ? this.type.get() : null;
    }
}
