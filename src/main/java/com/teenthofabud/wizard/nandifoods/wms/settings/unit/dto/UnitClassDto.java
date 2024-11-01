package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

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
@ToString
@Getter
@Setter
@SuperBuilder
public abstract class UnitClassDto {

    @OptionalEnumValidator(enumClazz = UnitClassLevelType.class, message = "Level type is invalid")
    protected Optional<String> level;

    protected Optional<String> name;

    protected Optional<String> description;

    protected Optional<String> longName;

    protected Optional<String> shortName;

    @OptionalEnumValidator(enumClazz = UnitClassStatus.class, message = "Status is invalid")
    protected Optional<String> status;

    protected Optional<@Valid UnitClassMeasuredValuesDto> metric;

    protected Optional<@Valid UnitClassMeasuredValuesDto> imperial;

    public UnitClassDto() {
        this.level = Optional.empty();
        this.name = Optional.empty();
        this.description = Optional.empty();
        this.longName = Optional.empty();
        this.shortName = Optional.empty();
        this.status = Optional.empty();
        this.metric = Optional.of(new UnitClassMeasuredValuesDto());
        this.imperial = Optional.of(new UnitClassMeasuredValuesDto());
    }
}
