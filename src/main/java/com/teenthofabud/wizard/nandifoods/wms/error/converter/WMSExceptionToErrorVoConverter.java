package com.teenthofabud.wizard.nandifoods.wms.error.converter;

import com.teenthofabud.wizard.nandifoods.wms.error.WMSMessageSource;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;

@Component
public class WMSExceptionToErrorVoConverter implements Converter<WMSBaseException, ErrorVo> {

    @Autowired
    private WMSMessageSource messageSource;

    @Override
    public ErrorVo convert(WMSBaseException source) {

        String msg = this.messageSource.getMessage(source.getError().getErrorCode(), (Object[])null, Locale.getDefault());
        if (source.getParameters() != null) {
            Deque<Object> parameters = new ArrayDeque(Arrays.asList(source.getParameters()));
            // Setting first parameter as the domain and subdomain info always
            String domainAndSubdomain = STR."\{source.getDomain()} - \{source.getSubDomain()}";
            parameters.addFirst(domainAndSubdomain);
            msg = String.format(msg, parameters.toArray(new Object[parameters.size()]));
        }
        return ErrorVo.builder()
                .errorCode(source.getError().getErrorCode())
                .domain(source.getDomain())
                .subDomain(source.getSubDomain())
                .message(msg)
                .build();
    }
}
