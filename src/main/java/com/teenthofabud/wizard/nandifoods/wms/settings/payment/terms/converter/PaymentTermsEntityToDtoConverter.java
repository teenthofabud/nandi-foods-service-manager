package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermsEntityToDtoConverter implements Converter<PaymentTermsEntity, PaymentTermsDto> {
    @Override
    public PaymentTermsDto convert(PaymentTermsEntity source) {
        return PaymentTermsDto.builder()
                .name(source.getName())
                .code(source.getCode())
                .daysUntilDue(source.getDaysUntilDue())
                .build();
    }
}
