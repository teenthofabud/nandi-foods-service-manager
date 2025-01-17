package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.entity.PaymentTermsEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PaymentTermsRepository extends JpaRepository<PaymentTermsEntity,Long>, PagingAndSortingRepository<PaymentTermsEntity,Long>, JpaSpecificationExecutor<PaymentTermsEntity> {

    public Optional<PaymentTermsEntity> findByCode(String code);

    default Specification<PaymentTermsEntity> likeProperty(String key, String value) {
        return (root, query, builder) -> builder.like(root.get(key).as(String.class), String.format("%%%s%%", value));
    }
}
