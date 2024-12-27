package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOMSelfLinkageJpaRepository extends JpaRepository<UOMSelfLinkageEntity, Long> {

    public void deleteByFromUomId(Long id);
    public void deleteByToUomId(Long id);

}
