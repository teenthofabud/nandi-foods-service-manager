package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;

import java.util.Optional;

public interface PaymentTermsService {

    PaymentTermsVo createNewPaymentTerms(PaymentTermsForm form);

    PaymentTermsVo retrieveExistingPaymentTermsByCode(String code);

    void deletePaymentTermsByCode(String code);

    void updatePaymentTermsByCode(String code, PaymentTermsDto paymentTermDto);

    PaymentTermsPageImplVo retrieveAllPaymentTerms(Optional<String> optionalQuery, PaymentTermsPageDto page);
}

