package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OptionalEnumValidatorImpl implements ConstraintValidator<OptionalEnumValidator, Optional<String>> {

    List<String> valueList = null;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        return value.isPresent() ? valueList.contains(value.get().toUpperCase()) : true;
    }

    @Override
    public void initialize(OptionalEnumValidator constraintAnnotation) {
        valueList = new ArrayList<String>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

}
