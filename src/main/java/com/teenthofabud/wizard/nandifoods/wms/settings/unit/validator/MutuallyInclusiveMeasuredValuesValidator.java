package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MutuallyInclusiveMeasuredValuesValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@NotNull(message = "Measured values cannot be null")
@ReportAsSingleViolation
public @interface MutuallyInclusiveMeasuredValuesValidator {

    MeasurementSystem[] measurementSystems();

    String message() default "Measured values for all measurement systems should be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
