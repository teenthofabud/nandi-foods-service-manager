package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaymentTermsPageImplVo extends PageImpl<PaymentTermsVo> {

    public PaymentTermsPageImplVo(List<PaymentTermsVo> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
