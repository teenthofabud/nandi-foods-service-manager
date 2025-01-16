package com.teenthofabud.wizard.nandifoods.wms.settings.unit.validator;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.MeasuredValuesContract;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Slf4j
public class MutuallyInclusiveMeasuredValuesValidatorImpl implements ConstraintValidator<MutuallyInclusiveMeasuredValuesValidator, List<? extends MeasuredValuesContract>> {

    List<MeasurementSystem> measurementSystems;

    @Override
    public boolean isValid(List<? extends MeasuredValuesContract> value, ConstraintValidatorContext context) {
        // disable existing violation message
        context.disableDefaultConstraintViolation();
        // value not empty
        if(ObjectUtils.isEmpty(value)) {
            context.buildConstraintViolationWithTemplate("Measured values are invalid").addConstraintViolation();
            return false;
        }
        // value has exactly 2 elements
        if(value.size() < measurementSystems.size() || value.size() > measurementSystems.size()) {
            context.buildConstraintViolationWithTemplate("Only " + measurementSystems.size() + " types of measured values should be provided").addConstraintViolation();
            return false;
        }
        // value does not have duplicate elements
        Set<? extends MeasuredValuesContract> uniqueValues = new LinkedHashSet<>(value);
        if(uniqueValues.size() != value.size()) {
            context.buildConstraintViolationWithTemplate("Measured values for all measurement systems not provided").addConstraintViolation();
            return false;
        }
        // value has elements at the correct index
        boolean hasCorrectIndices = measurementSystems.stream().map(f -> {
            Integer indexOfMeasuredValuesForMeasurementSystem = IntStream.range(0, value.size()).filter(i -> value.get(i).getMeasurementSystem().compareTo(f) == 0).findFirst().getAsInt();
            return indexOfMeasuredValuesForMeasurementSystem != f.getOrdinal();
        }).allMatch(Boolean::booleanValue);
        if(!hasCorrectIndices) {
            context.buildConstraintViolationWithTemplate("Incorrect index of measured values for measurement system").addConstraintViolation();
        }
        return hasCorrectIndices;
    }

    @Override
    public void initialize(MutuallyInclusiveMeasuredValuesValidator constraintAnnotation) {
        this.measurementSystems = Arrays.asList(constraintAnnotation.measurementSystems());
    }
}