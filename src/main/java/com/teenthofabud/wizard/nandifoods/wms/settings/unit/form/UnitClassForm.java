package com.teenthofabud.wizard.nandifoods.wms.settings.unit.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UnitClassLevelTypeValidator;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator.UntilDays;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.FirstOrder;
import com.teenthofabud.wizard.nandifoods.wms.validator.order.SecondOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@GroupSequence({ UnitClassForm.class, FirstOrder.class, SecondOrder.class })
public abstract class UnitClassForm {

    protected String level;

    @JsonProperty("name")
    protected String type;

    @NotBlank(message = "description is required")
    @Length(min = 1, message = "description should be at least 1 character long")
    protected String description;

    @Length(min = 8, max = 30, message = "long name should be between 8 and 30 characters")
    protected String longName;

    @Length(min = 8, max = 15, message = "short name should be between 8 and 15 characters")
    protected String shortName;

    @FutureOrPresent(groups = FirstOrder.class)
    @NotNull(message = "Effective date is required")
    @UntilDays(count = 91, groups = SecondOrder.class)
    protected LocalDate effectiveDate;

    @Size(min = 2, max = 2, message = "Both of imperial and metric measured values must be specified")
    protected List<@Valid UnitClassMeasuredValuesForm> measuredValues;

}
