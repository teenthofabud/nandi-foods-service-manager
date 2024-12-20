package com.teenthofabud.wizard.nandifoods.wms.converter;

import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Optional;

@Builder
public class OptionalStringToEnumTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<String> optionalEnumValName = (Optional<String>) value;

        if(optionalEnumValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }

        Enum[] enumConstants = (Enum[]) type.getEnumConstants();
        String enumValName = optionalEnumValName.get().toUpperCase();

        for (Enum enumConstant : enumConstants) {
            String enumConstantName = enumConstant.toString().toUpperCase();
            if (enumConstantName.equals(enumValName)) {
                return (T) enumConstant;
            }
        }

        throw new ConversionException(String.format("Failed to convert %s value to %s class", enumValName, type.toString()));
    }
}
