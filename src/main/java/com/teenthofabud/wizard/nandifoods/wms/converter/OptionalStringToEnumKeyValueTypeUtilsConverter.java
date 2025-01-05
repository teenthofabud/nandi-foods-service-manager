package com.teenthofabud.wizard.nandifoods.wms.converter;

import com.teenthofabud.wizard.nandifoods.wms.constants.EnumKeyValue;
import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Optional;

@Builder
public class OptionalStringToEnumKeyValueTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<String> optionalEnumValName = (Optional<String>) value;

        if(optionalEnumValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }
        EnumKeyValue[] enumConstants = (EnumKeyValue[]) type.getEnumConstants();
        String enumValName = optionalEnumValName.get().toUpperCase();
        for (EnumKeyValue enumConstant : enumConstants) {
            boolean isKey = enumConstant.getKey().toUpperCase().compareTo(enumValName.toUpperCase()) == 0;
            boolean isValue = enumConstant.getValue().toUpperCase().compareTo(enumValName.toUpperCase()) == 0;
            if (isKey || isValue) {
                return (T) enumConstant;
            }
        }
        throw new ConversionException(String.format("Failed to convert %s value to %s class", enumValName, type.toString()));
    }
}
