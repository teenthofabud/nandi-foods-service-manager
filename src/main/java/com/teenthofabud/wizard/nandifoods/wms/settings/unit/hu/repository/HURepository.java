package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.HUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HURepository extends JpaRepository<HUEntity, Long> {

}
