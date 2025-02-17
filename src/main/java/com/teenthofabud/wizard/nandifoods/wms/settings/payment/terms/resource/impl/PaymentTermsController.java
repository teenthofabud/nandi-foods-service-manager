package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.error.PaymentTermsException;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.resource.PaymentTermsAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.service.PaymentTermsService;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.UOMAPI;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = PaymentTermsAPI.BASE_URI)
public class PaymentTermsController implements PaymentTermsAPI {

    @Autowired
    private PaymentTermsService paymentTermsService;
    @Autowired
    private Validator validator;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> postPaymentTerms(@RequestBody @Valid PaymentTermsForm form) throws PaymentTermsException {
        PaymentTermsVo paymentTermsVo = paymentTermsService.createNewPaymentTerms(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paymentTermsVo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @PatchMapping(path = "/{code}", consumes = HttpMediaType.APPLICATION_JSON_PATCH)
    public ResponseEntity<Void> patchPaymentTermsByCode(@PathVariable String code, @RequestBody PaymentTermsDto sourceUOMDto) throws JsonPatchException, JsonProcessingException, PaymentTermsException {
        paymentTermsService.updatePaymentTermsByCode(code, sourceUOMDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentTermsVo> getPaymentTermsByCode(@PathVariable String code) throws PaymentTermsException {
        return ResponseEntity.ok(paymentTermsService.retrieveExistingPaymentTermsByCode(code));
    }


    @Override
    @DeleteMapping(path = "/{code}")
    public ResponseEntity<Void> deletePaymentTermsById(@PathVariable  String code) throws PaymentTermsException {
        paymentTermsService.deletePaymentTermsByCode(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentTermsPageImplVo> searchPaymentTermsByQuery(@RequestParam(required = false) String search,
                                                                            @RequestParam(required = false) String sort,
                                                                            @RequestParam(required = false) Boolean ascending,
                                                                            @RequestParam(required = false) Integer offset,
                                                                            @RequestParam(required = false) Long limit) {
        PaymentTermsPageDto paymentTermsPageDto = PaymentTermsPageDto.builder()
                .offset(Optional.ofNullable(offset))
                .sort(Optional.ofNullable(sort))
                .ascending(Optional.ofNullable(ascending))
                .limit(Optional.ofNullable(limit))
                .build();
        Set<ConstraintViolation<PaymentTermsPageDto>> violations = validator.validate(paymentTermsPageDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        PaymentTermsPageImplVo paymentTermsPageImplVo = paymentTermsService.retrieveAllPaymentTerms(Optional.ofNullable(search),paymentTermsPageDto);
        return ResponseEntity.ok(paymentTermsPageImplVo);
    }
}
