package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;


public class UnitClassLevelTypeValidatorImpl implements ConstraintValidator<UnitClassLevelTypeValidator, String> {

    boolean validateLevel = true;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validateLevel ? UnitClassLevelType.getAllLevels().contains(value) : UnitClassLevelType.getAllTypes().contains(value);
    }

    @Override
    public void initialize(UnitClassLevelTypeValidator constraintAnnotation) {
        validateLevel = constraintAnnotation.validateLevel();
    }

}
