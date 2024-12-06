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

    default Specification<UOMEntity> likeShortName(String shortName) {
        return (root, query, builder) -> builder.like(root.get("shortName").as(String.class), String.format("%%%s%%", shortName));
    }

    default Specification<UOMEntity> likeLongName(String longName) {
        return (root, query, builder) -> builder.like(root.get("longName").as(String.class), String.format("%%%s%%", longName));
    }

    default Specification<UOMEntity> likeDescription(String description) {
        return (root, query, builder) -> builder.like(root.get("description").as(String.class), String.format("%%%s%%", description));
    }

    default Specification<UOMEntity> likeCode(String code) {
        return (root, query, builder) -> builder.equal(root.get("code").as(String.class), code);
    }

    default Specification<UOMEntity> likeType(String type) {
        return (root, query, builder) -> builder.equal(root.get("type").as(String.class), type);
    }

    default Specification<UOMEntity> skipThisSpecificationWithEmptyCriteriaConjunction() {
        return (root, query, builder) -> builder.conjunction();
    }


}
