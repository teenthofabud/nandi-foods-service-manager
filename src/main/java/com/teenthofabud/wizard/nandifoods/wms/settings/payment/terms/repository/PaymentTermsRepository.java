package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PaymentTermsRepository extends JpaRepository<PaymentTermEntity,Long> {

    public Optional<PaymentTermEntity> findByCode(String code);
}
