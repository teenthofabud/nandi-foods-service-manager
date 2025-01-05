package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class UntilDaysValidatorImpl implements ConstraintValidator<UntilDays, LocalDate> {

    int count = 0;
    boolean mandatory;

    @Override
    public void initialize(UntilDays constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.count = constraintAnnotation.count();
        this.mandatory = constraintAnnotation.mandatory();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if(mandatory) {
            long differenceInDays = DAYS.between(LocalDate.now(), value);
            return differenceInDays >= 0 && differenceInDays <= count;
        }
        return true;
    }
}
