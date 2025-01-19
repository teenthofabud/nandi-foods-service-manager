package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.impl;

import com.teenthofabud.wizard.nandifoods.wms.handler.ComparativeUpdateHandler;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermsEntityToDtoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermsEntityToVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermsFormToEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter.PaymentTermsPageDtoToPageableConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository.PaymentTermsRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.PaymentTermsService;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
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
public class PaymentTermsServiceImpl implements PaymentTermsService, ComparativeUpdateHandler<PaymentTermsEntity> {

    @Autowired
    private PaymentTermsRepository paymentTermsRepository;
    @Autowired
    private PaymentTermsEntityToVoConverter paymentTermsEntityToVoConverter;
    @Autowired
    private PaymentTermsFormToEntityConverter paymentTermsFormToEntityConverter;
    @Autowired
    private PaymentTermsPageDtoToPageableConverter paymentTermsPageDtoToPageableConverter;
    @Autowired
    private PaymentTermsEntityToDtoConverter paymentTermsEntityToDtoConverter;
    @Autowired
    private Javers javers;
    @Autowired
    private BeanUtilsBean beanUtilsBean;

    @Value("#{'${wms.settings.paymentTerms.search.fields}'.split(',')}") List<String> searchFields;

    @Transactional
    @Override
    public PaymentTermsVo createNewPaymentTerms(PaymentTermsForm form) {
        Optional<PaymentTermsEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(form.getCode());
        if(optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term already exists with id: " + form.getCode());
        }

        PaymentTermsEntity paymentTermsEntity = paymentTermsFormToEntityConverter.convert(form);

        paymentTermsEntity = paymentTermsRepository.save(paymentTermsEntity);

        log.info("Payment term saved with id: {}", paymentTermsEntity.getId());
        PaymentTermsVo paymentsTermVo = paymentTermsEntityToVoConverter.convert(paymentTermsEntity);

        return paymentsTermVo;
    }

    @Override
    public PaymentTermsVo retrieveExistingPaymentTermsByCode(String code) {
        Optional<PaymentTermsEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermsEntity paymentTermsEntity = optionalPaymentTermsEntity.get();
        log.debug("Payment term found with code: {}", paymentTermsEntity.getCode());
        PaymentTermsVo paymentTermsVo = paymentTermsEntityToVoConverter.convert(paymentTermsEntity);
        return paymentTermsVo;
    }

    @Transactional
    @Override
    public void deletePaymentTermsByCode(String code) {
        Optional<PaymentTermsEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermsEntity paymentTermsEntity = optionalPaymentTermsEntity.get();
        log.debug("Payment term found with code: {}", paymentTermsEntity.getCode());
        paymentTermsRepository.delete(paymentTermsEntity);
        log.info("Payment term deleted with code: {}", paymentTermsEntity.getCode());

    }

    @Transactional
    @Override
    public void updatePaymentTermsByCode(String code, PaymentTermsDto sourceDto) {
        Optional<PaymentTermsEntity> optionalPaymentTermsEntity = paymentTermsRepository.findByCode(code);
        if(!optionalPaymentTermsEntity.isPresent()) {
            throw new IllegalStateException("Payment Term doesn't exist with code : " + code);
        }
        PaymentTermsEntity paymentTermsEntity = optionalPaymentTermsEntity.get();
        PaymentTermsDto targetDto = paymentTermsEntityToDtoConverter.convert(paymentTermsEntity);
        log.info("target : {}", targetDto);
        log.info("source : {}",sourceDto);
        Diff dtoDiff = javers.compare(targetDto,sourceDto);
        paymentTermsEntity = comparativelyUpdateMandatoryFields(dtoDiff,paymentTermsEntity,true);
        paymentTermsRepository.save(paymentTermsEntity);
        log.info("Payment term updated with code: {}", paymentTermsEntity.getCode());

    }

    @Transactional
    @Override
    public PaymentTermsPageImplVo retrieveAllPaymentTerms(Optional<String> optionalQuery, @Valid PaymentTermsPageDto pageDto) {
        Pageable pageable = paymentTermsPageDtoToPageableConverter.convert(pageDto);
        Page<PaymentTermsEntity> paymentTermsEntityPage = new PageImpl<>(List.of());
        if(optionalQuery.isPresent()) {
            String q = optionalQuery.get();
            List<Specification<PaymentTermsEntity>> paymentTermEntitySpecifications = searchFields.stream()
                    .flatMap(k -> Arrays.stream(q.split("\\s+"))
                            .map(v ->
                                    paymentTermsRepository.likeProperty(k, v))).collect(Collectors.toList());
            Specification<PaymentTermsEntity> paymentTermSearchQuerySpecification = Specification.anyOf(paymentTermEntitySpecifications);
            log.debug("Created specification for PaymentTermEntity using query: {}", q);
            paymentTermsEntityPage = paymentTermsRepository.findAll(paymentTermSearchQuerySpecification, pageable);
        } else {
            log.debug("Searching PaymentTermEntity with page: {}", pageable);
            paymentTermsEntityPage = paymentTermsRepository.findAll(pageable);
        }
        List<PaymentTermsVo> paymentTermsVosList = paymentTermsEntityPage.stream().map(i -> {
            PaymentTermsVo paymentTermsVo = paymentTermsEntityToVoConverter.convert(i);
            return paymentTermsVo;
        }).collect(Collectors.toList());
        PaymentTermsPageImplVo paymentTermsPageImplVo = new PaymentTermsPageImplVo(paymentTermsVosList, paymentTermsEntityPage.getPageable(), paymentTermsEntityPage.getTotalElements());
        log.debug("Found {} Payment Term in page {}", paymentTermsEntityPage.getTotalElements(), paymentTermsEntityPage.getNumber());
        return paymentTermsPageImplVo;
    }

    @Override
    public BeanUtilsBean getBeanUtilsBean() {
        return this.beanUtilsBean;
    }
}
