package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class UnitClassLevelTypeValidatorImpl implements ConstraintValidator<UnitClassLevelTypeValidator, UnitClassForm> {

    @Override
    public boolean isValid(UnitClassForm value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value) || !StringUtils.hasText(value.getLevel()) || !StringUtils.hasText(value.getType())) {
            return false;
        }
        boolean flag = false;
        try {
            UnitClassLevelType level = UnitClassLevelType.getByLevel(value.getLevel());
            UnitClassLevelType type = UnitClassLevelType.getByType(value.getType());
            flag = level.compareTo(type) == 0;
        } catch (NoSuchElementException e) {
        }
        return flag;
    }

    @Override
    public void initialize(UnitClassLevelTypeValidator constraintAnnotation) {
    }

}
