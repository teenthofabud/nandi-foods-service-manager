package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.projections.UOMSummaryProjection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UOMJpaRepository extends JpaRepository<UOMEntity, Long>, PagingAndSortingRepository<UOMEntity, Long>, JpaSpecificationExecutor<UOMEntity> {

    public Optional<UOMEntity> findByCode(String code);

    // select u.code as uom_id, u.level_type as uom_name, u.description as description, u.long_name as uom_long_name, u.short_name as uom_short_name, mv.weight_value as weight, mv.weight_unit, u.bulk_code as bulk_code from settings.uom u left outer join settings.uom_measured_values mv on u.id = mv.uom_id where mv.metric_system = 'SI';
    /*@Query(
        """
        select new 
        com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.projections.UOMSummaryProjection(
        u.levelType, 
        u.description, 
        u.longName, 
        u.shortName, 
        u.code, 
        u.bulkCode, 
        com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.projections.UnitClassMeasuredValuesSummaryProjection(
        mv.weightValue, 
        mv.weightUnit)) 
        from UOMEntity u left join UOMMeasuredValuesEntity mv 
        on u.id = mv.uom.id 
        where mv.metricSystem = :metricSystem
        """
    )
    public List<UOMSummaryProjection> findAllWithMeasuredValueForMetricSystem(MetricSystem metricSystem);*/

    default Specification<UOMEntity> likeProperty(String key, String value) {
        return (root, query, builder) -> builder.like(root.get(key).as(String.class), String.format("%%%s%%", value));
    }

    default Specification<UOMEntity> equalsProperty(String key, Object value) {
        return (root, query, builder) -> builder.equal(root.get(key).as(String.class), value);
    }

    default Specification<UOMEntity> skipThisSpecificationWithEmptyCriteriaConjunction() {
        return (root, query, builder) -> builder.conjunction();
    }

}
