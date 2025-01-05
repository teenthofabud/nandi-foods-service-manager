package com.teenthofabud.wizard.nandifoods.wms.converter;

import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Optional;

@Builder
public class OptionalStringToIntegerTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<String> integerValName = (Optional<String>) value;
        if(integerValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }
        return (T) Integer.valueOf(integerValName.get());
    }
}
