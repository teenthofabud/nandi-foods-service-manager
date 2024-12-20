package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UntilDaysValidatorImpl.class)
@Documented
public @interface UntilDays {
    String message() default "Date can't be greater than {count} days from now";
    Class<?>[] groups() default {};
    int count();
    Class<? extends Payload>[] payload() default {};
}
