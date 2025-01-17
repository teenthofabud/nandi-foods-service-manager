package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermsFormToEntityConverter implements Converter<PaymentTermsForm, PaymentTermsEntity> {


    @Override
    public PaymentTermsEntity convert(PaymentTermsForm source) {
        return PaymentTermsEntity.builder()
                .name(source.getName())
                .code(source.getCode())
                .daysUntilDue(source.getDaysUntilDue())
                .build();
    }
}
