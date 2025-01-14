package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermEntityToVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermFormToEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository.PaymentTermsRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.PaymentTermService;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentTermServiceImpl implements PaymentTermService {

    @Autowired
    private PaymentTermsRepository paymentTermsRepository;

    @Autowired
    private PaymentTermEntityToVoConverter paymentTermEntityToVoConverter;
    @Autowired
    private PaymentTermFormToEntityConverter paymentTermFormToEntityConverter;

    @Transactional
    @Override
    public PaymentTermVo createNewPaymentTerm(PaymentTermForm form) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(form.getCode());
        if(optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term already exists with id: " + form.getCode());
        }

        PaymentTermEntity paymentTermsEntity = paymentTermFormToEntityConverter.convert(form);

        paymentTermsEntity = paymentTermsRepository.save(paymentTermsEntity);

        log.info("Payment term saved with id: {}", paymentTermsEntity.getId());
        PaymentTermVo paymentTermVo = paymentTermEntityToVoConverter.convert(paymentTermsEntity);

        return paymentTermVo;
    }

    @Override
    public PaymentTermVo retrieveExistingPaymentTermByCode(String code) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermEntity paymentTermEntity = optionalPaymentTermsEntity.get();
        log.debug("Payment term found with code: {}", paymentTermEntity.getCode());
        PaymentTermVo paymentTermVo = paymentTermEntityToVoConverter.convert(paymentTermEntity);
        return paymentTermVo;
    }

    @Transactional
    @Override
    public void deletePaymentTermByCode(String code) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermEntity paymentTermEntity = optionalPaymentTermsEntity.get();
        log.debug("Payment term found with code: {}", paymentTermEntity.getCode());
        paymentTermsRepository.delete(paymentTermEntity);
        log.info("Payment term deleted with code: {}", paymentTermEntity.getCode());

    }

    @Transactional
    @Override
    public void updatePaymentTermByCode(String code, PaymentTermForm form) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermEntity paymentTermEntity = optionalPaymentTermsEntity.get();
        paymentTermEntity.setName(form.getName());
        paymentTermEntity.setDaysUntilDue(form.getDaysUntilDue());
        paymentTermsRepository.save(paymentTermEntity);
        log.info("Payment term updated with code: {}", paymentTermEntity.getCode());

    }

    @Transactional
    @Override
    public List<PaymentTermVo> retrieveAllPaymentTerms(Optional<String> optionalQuery) {
        List<PaymentTermEntity> paymentTerms = paymentTermsRepository.findAll();
        List<PaymentTermVo> paymentTermVos = new ArrayList();
        paymentTerms.forEach(elt-> { paymentTermVos.add(paymentTermEntityToVoConverter.convert(elt));});
        return paymentTermVos;
    }
}
