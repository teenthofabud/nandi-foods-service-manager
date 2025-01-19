package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermsEntityToVoConverter implements Converter<PaymentTermsEntity, PaymentTermsVo> {
    @Override
    public PaymentTermsVo convert(PaymentTermsEntity source) {
        return PaymentTermsVo.builder()
                .name(source.getName())
                .code(source.getCode())
                .id(source.getId())
                .daysUntilDue(source.getDaysUntilDue())
                .build();
    }
}
