package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.UnitMetadataAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitMetadataService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.MetricSystemVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = UnitMetadataAPI.BASE_URI)
public class UnitMetadataController implements UnitMetadataAPI {

    private UnitMetadataService unitMetadataService;

    @Autowired
    public UnitMetadataController(UnitMetadataService unitMetadataService) {
        this.unitMetadataService = unitMetadataService;
    }

    @Override
    public String getContext() {
        return "UnitMetadata";
    }

    @GetMapping(path = "/metricSystem", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<MetricSystemVo>> getMetricSystems() {
        List<MetricSystemVo> metricSystemVoList = unitMetadataService.retrieveExistingMetricSystems();
        return ResponseEntity.ok(metricSystemVoList);
    }

    @GetMapping(path = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassTypeVo>> getUnitClassTypes() {
        List<UnitClassTypeVo> unitClassTypeVoList = unitMetadataService.retrieveExistingUnitClassTypes();
        return ResponseEntity.ok(unitClassTypeVoList);
    }

    @GetMapping(path = "/level", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassLevelVo>> getUnitClassLevels() {
        List<UnitClassLevelVo> unitClassLevelVoList = unitMetadataService.retrieveExistingUnitClassLevels();
        return ResponseEntity.ok(unitClassLevelVoList);
    }

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassStatusVo>> getUnitClassStatuses() {
        List<UnitClassStatusVo> unitClassStatusVoList = unitMetadataService.retrieveExistingUnitClassLStatuses();
        return ResponseEntity.ok(unitClassStatusVoList);
    }
}
