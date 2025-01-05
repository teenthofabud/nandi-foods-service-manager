package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassLevelContract;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.NoSuchElementException;


public class UnitClassLevelTypeValidatorImpl implements ConstraintValidator<UnitClassLevelTypeValidator, UnitClassLevelContract> {

    boolean mandatory;

    private boolean matchLevelAndType(String level, String type) {
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
    public boolean isValid(UnitClassLevelContract value, ConstraintValidatorContext context) {
        if(mandatory) {
            if(ObjectUtils.isEmpty(value)) {
                return false;
            }
            String level = value.getLevelValue();
            String type = value.getTypeValue();
            if(!StringUtils.hasText(level) || !StringUtils.hasText(type)) {
                return false;
            }
            boolean flag = this.matchLevelAndType(level, type);
            return flag;
        } else {
            if(!ObjectUtils.isEmpty(value)) {
                String level = value.getLevelValue();
                String type = value.getTypeValue();
                if((StringUtils.hasText(level) && !StringUtils.hasText(type)) || (!StringUtils.hasText(level) && StringUtils.hasText(type))) {
                    return false;
                }
                if(StringUtils.hasText(level) && StringUtils.hasText(type)) {
                    boolean flag = this.matchLevelAndType(level, type);
                    return flag;
                }
            }
            return true;
        }
    }

    @Override
    public void initialize(UnitClassLevelTypeValidator constraintAnnotation) {
        this.mandatory = constraintAnnotation.mandatory();
    }

}
