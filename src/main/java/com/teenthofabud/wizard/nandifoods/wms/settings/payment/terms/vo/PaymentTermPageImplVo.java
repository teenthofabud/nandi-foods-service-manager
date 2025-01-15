package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaymentTermPageImplVo extends PageImpl<PaymentTermVo> {

    public PaymentTermPageImplVo(List<PaymentTermVo> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
