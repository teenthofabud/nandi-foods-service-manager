package com.teenthofabud.wizard.nandifoods.wms.validator;

import com.teenthofabud.wizard.nandifoods.wms.constants.EnumKeyValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.*;


public class OptionalEnumKeyValueValidatorImpl implements ConstraintValidator<OptionalEnumKeyValueValidator, Optional<String>> {

    Map<String, String> valueMap = null;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        return value.isPresent() ? valueMap.entrySet().stream()
                    .filter(f -> f.getValue().compareTo(value.get()) == 0 || f.getKey().compareTo(value.get()) == 0)
                    .findFirst().orElse(null) != null : true;
    }

    @Override
    public void initialize(OptionalEnumKeyValueValidator constraintAnnotation) {
        valueMap = new LinkedHashMap<>();
        Class<? extends EnumKeyValue<?>> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        EnumKeyValue[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") EnumKeyValue enumVal : enumValArr) {
            valueMap.put(enumVal.getKey(), enumVal.getValue());
        }
    }

}
