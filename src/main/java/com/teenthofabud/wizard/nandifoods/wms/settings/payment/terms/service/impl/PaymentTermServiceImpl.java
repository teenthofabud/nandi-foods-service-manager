package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermEntityToVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermFormToEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermPageDtoToPageableConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository.PaymentTermRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.PaymentTermService;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentTermServiceImpl implements PaymentTermService {

    @Autowired
    private PaymentTermRepository paymentTermRepository;
    @Autowired
    private PaymentTermEntityToVoConverter paymentTermEntityToVoConverter;
    @Autowired
    private PaymentTermFormToEntityConverter paymentTermFormToEntityConverter;
    @Autowired
    private PaymentTermPageDtoToPageableConverter paymentTermPageDtoToPageableConverter;
    @Value("#{'${wms.settings.payment_terms.search.fields}'.split(',')}") List<String> searchFields;

    @Transactional
    @Override
    public PaymentTermVo createNewPaymentTerm(PaymentTermForm form) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermRepository.findByCode(form.getCode());
        if(optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term already exists with id: " + form.getCode());
        }

        PaymentTermEntity paymentTermsEntity = paymentTermFormToEntityConverter.convert(form);

        paymentTermsEntity = paymentTermRepository.save(paymentTermsEntity);

        log.info("Payment term saved with id: {}", paymentTermsEntity.getId());
        PaymentTermVo paymentTermVo = paymentTermEntityToVoConverter.convert(paymentTermsEntity);

        return paymentTermVo;
    }

    @Override
    public PaymentTermVo retrieveExistingPaymentTermByCode(String code) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermRepository.findByCode(code);
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
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermEntity paymentTermEntity = optionalPaymentTermsEntity.get();
        log.debug("Payment term found with code: {}", paymentTermEntity.getCode());
        paymentTermRepository.delete(paymentTermEntity);
        log.info("Payment term deleted with code: {}", paymentTermEntity.getCode());

    }

    @Transactional
    @Override
    public void updatePaymentTermByCode(String code, PaymentTermForm form) {
        Optional<PaymentTermEntity> optionalPaymentTermsEntity = paymentTermRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermEntity paymentTermEntity = optionalPaymentTermsEntity.get();
        paymentTermEntity.setName(form.getName());
        paymentTermEntity.setDaysUntilDue(form.getDaysUntilDue());
        paymentTermRepository.save(paymentTermEntity);
        log.info("Payment term updated with code: {}", paymentTermEntity.getCode());

    }

    @Transactional
    @Override
    public PaymentTermPageImplVo retrieveAllPaymentTerms(Optional<String> optionalQuery, @Valid PaymentTermPageDto pageDto) {
        Pageable pageable = paymentTermPageDtoToPageableConverter.convert(pageDto);
        Page<PaymentTermEntity> paymentTermEntityPage = new PageImpl<>(List.of());
        if(optionalQuery.isPresent()) {
            String q = optionalQuery.get();
            List<Specification<PaymentTermEntity>> paymentTermEntitySpecifications = searchFields.stream()
                    .flatMap(k -> Arrays.stream(q.split("\\s+"))
                            .map(v ->
                                    paymentTermRepository.likeProperty(k, v))).collect(Collectors.toList());
            Specification<PaymentTermEntity> paymentTermSearchQuerySpecification = Specification.anyOf(paymentTermEntitySpecifications);
            log.debug("Created specification for PaymentTermEntity using query: {}", q);
            paymentTermEntityPage = paymentTermRepository.findAll(paymentTermSearchQuerySpecification, pageable);
        } else {
            log.debug("Searching PaymentTermEntity with page: {}", pageable);
            paymentTermEntityPage = paymentTermRepository.findAll(pageable);
        }
        List<PaymentTermVo> paymentTermVosList = paymentTermEntityPage.stream().map(i -> {
            PaymentTermVo paymentTermVo = paymentTermEntityToVoConverter.convert(i);
            return paymentTermVo;
        }).collect(Collectors.toList());
        PaymentTermPageImplVo paymentTermPageImplVo = new PaymentTermPageImplVo(paymentTermVosList, paymentTermEntityPage.getPageable(), paymentTermEntityPage.getTotalElements());
        log.debug("Found {} Payment Term in page {}", paymentTermEntityPage.getTotalElements(), paymentTermEntityPage.getNumber());
        return paymentTermPageImplVo;
    }
}
