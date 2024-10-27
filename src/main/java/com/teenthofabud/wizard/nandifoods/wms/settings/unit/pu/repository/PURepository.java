package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.PUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PURepository extends JpaRepository<PUEntity, Long> {

}
