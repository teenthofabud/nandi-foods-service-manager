package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.repository;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.HUEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.PUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HURepository extends JpaRepository<HUEntity, Long> {

    public Optional<HUEntity> findByCode(String code);

}
