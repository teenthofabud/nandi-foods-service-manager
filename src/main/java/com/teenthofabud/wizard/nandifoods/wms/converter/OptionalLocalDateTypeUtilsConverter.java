package com.teenthofabud.wizard.nandifoods.wms.converter;

import lombok.Builder;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import java.time.LocalDate;
import java.util.Optional;

@Builder
public class OptionalLocalDateTypeUtilsConverter implements Converter {
    @Override
    public <T> T convert(Class<T> type, Object value) {
        Optional<LocalDate> localDateValName = (Optional<LocalDate>) value;
        if(localDateValName.isEmpty()) {
            throw new ConversionException("No value provided for conversion");
        }
        LocalDate ld = localDateValName.get();
        return (T) ld;
    }
}
