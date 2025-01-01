package com.teenthofabud.wizard.nandifoods.wms.converter;

import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.util.Optional;

@Builder
public class OptionalBooleanTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<Boolean> booleanValName = (Optional<Boolean>) value;
        if(booleanValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }
        Boolean b = booleanValName.get();
        return (T) b;
    }
}
