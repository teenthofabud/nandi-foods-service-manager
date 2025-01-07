package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassLevelContract;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.util.NoSuchElementException;


public class UnitClassLevelTypeValidatorImpl implements ConstraintValidator<UnitClassLevelTypeValidator, UnitClassLevelContract> {

    @Override
    public boolean isValid(UnitClassLevelContract value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)) {
            return false;
        }
        UnitClassLevel level = value.getLevel();
        UnitClassType type = value.getType();
        if(ObjectUtils.isEmpty(level) || ObjectUtils.isEmpty(type)) {
            return false;
        }
        boolean flag = false;
        try {
            UnitClassLevelType levelConstant = UnitClassLevelType.getByLevel(level);
            UnitClassLevelType typeConstant = UnitClassLevelType.getByType(type);
            flag = levelConstant.compareTo(typeConstant) == 0;
        } catch (NoSuchElementException e) {
        }
        return flag;
    }

    @Override
    public void initialize(UnitClassLevelTypeValidator constraintAnnotation) {

    }

}
