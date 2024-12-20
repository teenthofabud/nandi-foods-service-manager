package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@UnitClassLevelTypeValidator
public abstract class UnitClassForm {

    protected String level;

    protected String type;

    @NotBlank(message = "description is required")
    @Length(min = 1, message = "description should be at least 1 character long")
    protected String description;

    protected String longName;

    protected String shortName;

    @FutureOrPresent
    @UntilDays(count = 91)
    protected LocalDate effectiveDate;

    @Size(min = 2, max = 2, message = "Both of imperial and metric measured values must be specified")
    protected List<@Valid UnitClassMeasuredValuesForm> measuredValues;

}
