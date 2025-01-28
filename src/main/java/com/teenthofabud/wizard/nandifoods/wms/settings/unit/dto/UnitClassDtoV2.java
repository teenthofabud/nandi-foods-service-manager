package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassLevelContract;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.MutuallyInclusiveMeasuredValuesValidator;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.FirstOrder;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.SecondOrder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.javers.core.metamodel.annotation.DiffIgnore;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@UnitClassLevelTypeValidator
@SuperBuilder
@GroupSequence({ UnitClassDtoV2.class, FirstOrder.class, SecondOrder.class })
public abstract class UnitClassDtoV2 implements UnitClassLevelContract {

    @Setter
    @JsonSetter(nulls = Nulls.SKIP)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "UOM Level")
    // validated by class level annotation @UnitClassLevelTypeValidator
    protected UnitClassLevel level;

    @Getter
    @Setter
    @JsonSetter(nulls = Nulls.SKIP)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1 X 4LB", description = "Description of the UOM")
    protected UnitClassStatus status;

    @Setter
    @JsonSetter(nulls = Nulls.SKIP)
    @JsonProperty("name")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "UOM Type")
    // validated by class level annotation @UnitClassLevelTypeValidator
    protected UnitClassType type;

    @Getter
    @Setter
    @JsonSetter(nulls = Nulls.SKIP)
    @Length(min = 1, message = "description should be at least 1 character long")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1 X 4LB", description = "Description of the UOM")
    protected String description;

    @Getter
    @Setter
    @Length(min = 8, max = 15, message = "short name should be between 8 and 15 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "EACH (U1009)", description = "[UOM Type] (UOM ID)")
    protected String shortName;

    @Getter
    @Setter
    @FutureOrPresent(groups = FirstOrder.class)
    @NotNull(message = "effective date is required")
    @UntilDays(mandatory = false, count = 91, groups = SecondOrder.class)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "yyy-MM-dd")
    protected LocalDate effectiveDate;

    @Getter
    @Setter
    @JsonIgnore // not editable
    @DiffIgnore
    protected String code;

    @Getter
    @Setter
    @DiffIgnore
    @MutuallyInclusiveMeasuredValuesValidator(measurementSystems = { MeasurementSystem.SI, MeasurementSystem.IMPERIAL })
    @ArraySchema(schema = @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = UnitClassMeasuredValuesDtoV2.class,
            description = "measured values in supported measurement systems"))
    protected List<@Valid UnitClassMeasuredValuesDtoV2> measuredValues;

    @Override
    public UnitClassLevel getLevel() {
        return this.level;
    }

    @Override
    public UnitClassType getType() {
        return this.type;
    }

}
