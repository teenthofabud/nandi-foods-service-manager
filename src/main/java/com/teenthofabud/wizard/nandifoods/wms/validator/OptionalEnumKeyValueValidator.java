package com.teenthofabud.wizard.nandifoods.wms.validator;

import com.teenthofabud.wizard.nandifoods.wms.constants.EnumKeyValue;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionalEnumKeyValueValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE_USE})
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface OptionalEnumKeyValueValidator {

    Class<? extends EnumKeyValue<?>> enumClazz();

    String message() default "Value is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
