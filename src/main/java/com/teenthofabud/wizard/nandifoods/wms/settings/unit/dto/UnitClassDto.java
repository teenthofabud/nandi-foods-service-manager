package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.OptionalEnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassDto {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @OptionalEnumValidator(enumClazz = UnitClassLevelType.class, message = "Level type is invalid")
    protected Optional<String> levelType = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @OptionalEnumValidator(enumClazz = UnitClassStatus.class, message = "Status is invalid")
    protected Optional<String> status = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> name = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> description = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> longName = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> shortName = Optional.ofNullable(null);

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Size(min = 1, max = 2, message = "Either or both of imperial and metric measured values must be specified") List<@Valid UnitClassMeasuredValuesDto>> metric = Optional.of(new ArrayList<>());

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
}
