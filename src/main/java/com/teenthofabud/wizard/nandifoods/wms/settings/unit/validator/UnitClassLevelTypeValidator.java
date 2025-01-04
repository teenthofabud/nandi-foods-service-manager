package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UnitClassLevelTypeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface UnitClassLevelTypeValidator {

    boolean mandatory() default true;

    String message() default "Level and type does not belong to each other";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
