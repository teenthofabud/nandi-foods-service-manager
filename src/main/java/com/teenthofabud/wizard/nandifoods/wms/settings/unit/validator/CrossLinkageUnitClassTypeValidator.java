package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CrossLinkageUnitClassTypeValidator implements ConstraintValidator<CrossLinkageUnitClassType, UnitClass> {
    private UnitClass[] subset;

    @Override
    public void initialize(CrossLinkageUnitClassType constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(UnitClass value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}