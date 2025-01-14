package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermVo;

import java.util.List;
import java.util.Optional;

public interface PaymentTermService {

    PaymentTermVo createNewPaymentTerm(PaymentTermForm form);

    PaymentTermVo retrieveExistingPaymentTermByCode(String code);

    void deletePaymentTermByCode(String code);

    void updatePaymentTermByCode(String code, PaymentTermForm form);

    List<PaymentTermVo> retrieveAllPaymentTerms(Optional<String> optionalQuery);
}

