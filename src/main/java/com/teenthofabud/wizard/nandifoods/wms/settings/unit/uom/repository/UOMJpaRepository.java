package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UOMJpaRepository extends JpaRepository<UOMEntity, Long>, PagingAndSortingRepository<UOMEntity, Long>, JpaSpecificationExecutor<UOMEntity> {

    public Optional<UOMEntity> findByCode(String code);

    default Specification<UOMEntity> likeShortName(UOMEntity uomEntity) {
        return (root, query, builder) -> builder.like(root.get("shortName").as(String.class), String.format("%%%s%%", uomEntity.getShortName()));
    }

    default Specification<UOMEntity> equalsId(UOMEntity uomEntity) {
        return (root, query, builder) -> builder.equal(root.get("code").as(String.class), uomEntity.getCode());
    }

    default Specification<UOMEntity> equalsType(UOMEntity uomEntity) {
        return (root, query, builder) -> builder.equal(root.get("type").as(String.class), uomEntity.getType());
    }

}
