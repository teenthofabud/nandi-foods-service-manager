package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.error.PaymentTermsException;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;

import java.util.Optional;

public interface PaymentTermsService {

    PaymentTermsVo createNewPaymentTerms(PaymentTermsForm form) throws PaymentTermsException;

    PaymentTermsVo retrieveExistingPaymentTermsByCode(String code) throws PaymentTermsException;

    void deletePaymentTermsByCode(String code) throws PaymentTermsException;

    void updatePaymentTermsByCode(String code, PaymentTermsDto paymentTermDto) throws PaymentTermsException;

    PaymentTermsPageImplVo retrieveAllPaymentTerms(Optional<String> optionalQuery, PaymentTermsPageDto page);
}

