package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermForm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermFormToEntityConverter implements Converter<PaymentTermForm, PaymentTermEntity> {


    @Override
    public PaymentTermEntity convert(PaymentTermForm source) {
        return PaymentTermEntity.builder()
                .name(source.getName())
                .code(source.getCode())
                .daysUntilDue(source.getDaysUntilDue())
                .build();
    }
}
