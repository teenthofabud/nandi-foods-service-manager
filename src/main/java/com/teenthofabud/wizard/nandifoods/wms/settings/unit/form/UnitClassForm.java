package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.FirstOrder;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.SecondOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
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
@GroupSequence({ UnitClassForm.class, FirstOrder.class, SecondOrder.class })
public abstract class UnitClassForm {

    protected String level;

    protected String type;

    @NotBlank(message = "description is required")
    @Length(min = 1, message = "description should be at least 1 character long")
    protected String description;

    protected String longName;

    protected String shortName;

    @FutureOrPresent(groups = FirstOrder.class)
    @NotNull(message = "Effective date is required")
    @UntilDays(count = 91, groups = SecondOrder.class)
    protected LocalDate effectiveDate;

    @Size(min = 2, max = 2, message = "Both of imperial and metric measured values must be specified")
    protected List<@Valid UnitClassMeasuredValuesForm> measuredValues;

}
