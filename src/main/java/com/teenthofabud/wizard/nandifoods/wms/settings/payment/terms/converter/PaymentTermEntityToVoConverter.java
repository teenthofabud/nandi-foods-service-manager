package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermVo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermEntityToVoConverter implements Converter<PaymentTermEntity, PaymentTermVo> {
    @Override
    public PaymentTermVo convert(PaymentTermEntity source) {
        return PaymentTermVo.builder()
                .name(source.getName())
                .code(source.getCode())
                .daysUntilDue(source.getDaysUntilDue())
                .build();
    }
}
