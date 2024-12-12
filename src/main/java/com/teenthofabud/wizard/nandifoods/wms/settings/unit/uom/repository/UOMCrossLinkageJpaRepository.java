package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMCrossLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOMCrossLinkageJpaRepository extends JpaRepository<UOMCrossLinkageEntity, Long> {

}
