package com.teenthofabud.wizard.nandifoods.wms.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionalTypeAttributeValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface OptionalTypeAttributeValidator {

    Class<?> clazz();

    String message() default "Value is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
