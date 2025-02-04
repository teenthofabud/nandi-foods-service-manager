package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UOMMeasuredValuesJpaRepository extends JpaRepository<UOMMeasuredValuesEntity, Long> {

    public void deleteByUomId(Long id);
    public void deleteByUom(UOMEntity uom);

}
