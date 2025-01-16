package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassLevelContract;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.FirstOrder;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.SecondOrder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@UnitClassLevelTypeValidator
@GroupSequence({ UnitClassForm.class, FirstOrder.class, SecondOrder.class })
public abstract class UnitClassForm implements UnitClassLevelContract {

    // not editable in edit workflow
    @Getter
    @Setter
    @JsonProperty("id")
    @Pattern(regexp = "U(100[1-9]|10[1-9][0-9]|1[1-9][0-9]{2}|[2-4][0-9]{3})", message = "id value is invalid")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "U1001", description = "UOM ID")
    protected String code;

    @Setter
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "UOM Level")
    // validated by class level annotation @UnitClassLevelTypeValidator
    protected UnitClassLevel level;

    @Setter
    @JsonProperty("name")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "UOM Type")
    // validated by class level annotation @UnitClassLevelTypeValidator
    protected UnitClassType type;

    @Getter
    @Setter
    @Length(min = 1, message = "description should be at least 1 character long")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1 X 4LB", description = "Description of the UOM")
    protected String description;

    @Getter
    @Setter
    // not editable in edit workflow
    @Length(min = 8, max = 30, message = "long name should be between 8 and 30 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "U1009 EACH (3 X 4LB)", description = "[UOM ID] [UOM Type] (Description)")
    protected String longName;

    @Getter
    @Setter
    @Length(min = 8, max = 15, message = "short name should be between 8 and 15 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "EACH (U1009)", description = "[UOM Type] (UOM ID)")
    protected String shortName;

    @Getter
    @Setter
    @FutureOrPresent(groups = FirstOrder.class)
    @NotNull(message = "Effective date is required")
    @UntilDays(count = 91, groups = SecondOrder.class)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "yyy-MM-dd")
    protected LocalDate effectiveDate;

    @Getter
    @Setter
    @Size(min = 2, max = 2, message = "Both of imperial and metric measured values must be specified")
    @ArraySchema(schema = @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = UnitClassMeasuredValuesForm.class,
            description = "measured values in supported measurement systems"))
    protected Set<@Valid UnitClassMeasuredValuesForm> measuredValues;

    @Override
    public UnitClassLevel getLevel() {
        return this.level;
    }

    @Override
    public UnitClassType getType() {
        return this.type;
    }
}
