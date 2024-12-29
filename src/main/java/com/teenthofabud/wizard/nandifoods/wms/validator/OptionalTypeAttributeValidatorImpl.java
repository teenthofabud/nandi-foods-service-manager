package com.teenthofabud.wizard.nandifoods.wms.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class OptionalTypeAttributeValidatorImpl implements ConstraintValidator<OptionalTypeAttributeValidator, Optional<String>> {

    List<String> attributesOfType = null;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        return value.isPresent() ? attributesOfType.contains(value.get()) : true;
    }

    @Override
    public void initialize(OptionalTypeAttributeValidator constraintAnnotation) {
        attributesOfType = new ArrayList<String>();
        Class<?> clazz = constraintAnnotation.clazz();
        List<Field> fields = new ArrayList<>();
        while(clazz.getSuperclass() != null){ // we don't want to process Object.class
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        attributesOfType = fields.stream().map(f -> f.getName()).collect(Collectors.toList());
    }

}
