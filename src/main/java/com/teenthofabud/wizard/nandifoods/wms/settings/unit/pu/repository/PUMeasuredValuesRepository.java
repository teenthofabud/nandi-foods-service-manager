package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.PUMeasuredValuesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PUMeasuredValuesRepository extends JpaRepository<PUMeasuredValuesEntity, Long> {

}
