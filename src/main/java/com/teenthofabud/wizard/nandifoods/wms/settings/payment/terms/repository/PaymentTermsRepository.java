package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTermsRepository extends JpaRepository<PaymentTermsEntity,Long> {

    public Optional<PaymentTermsEntity> findByCode(String code);
}
