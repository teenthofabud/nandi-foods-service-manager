package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.EnumValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.OptionalEnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

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
    protected Optional<@EnumValidator(enumClazz = UnitClassLevelType.class, message = "Level type is invalid") String> level = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@EnumValidator(enumClazz = UnitClassStatus.class, message = "Status is invalid") String> status = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> name = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> description = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> longName = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<String> shortName = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Valid UnitClassMeasuredValuesDto> metric = Optional.of(new UnitClassMeasuredValuesDto());

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    protected Optional<@Valid UnitClassMeasuredValuesDto> imperial = Optional.of(new UnitClassMeasuredValuesDto());

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
