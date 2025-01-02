package com.teenthofabud.wizard.nandifoods.wms.converter;

import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Optional;

@Builder
public class OptionalStringTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<String> stringValName = (Optional<String>) value;
        if(stringValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }
        String s = stringValName.get();
        return (T) s;
    }
}
