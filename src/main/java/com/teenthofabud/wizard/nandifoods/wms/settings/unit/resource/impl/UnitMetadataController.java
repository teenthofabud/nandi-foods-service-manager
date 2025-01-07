package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.UnitMetadataAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitMetadataService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.MeasurementSystemVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelTypeVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassConstantVo;
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
    public ResponseEntity<List<MeasurementSystemVo>> getMetricSystems() {
        /*List<MeasurementSystemVo> measurementSystemVoList = unitMetadataService.retrieveExistingMetricSystems();
        return ResponseEntity.ok(measurementSystemVoList);*/
        return null;
    }

    @GetMapping(path = "/measurementSystem", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<MeasurementSystemVo>> getMeasurementSystems() {
        List<MeasurementSystemVo> measurementSystemVoList = unitMetadataService.retrieveExistingMeasurementSystems();
        return ResponseEntity.ok(measurementSystemVoList);
    }

    @GetMapping(path = "/class", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassConstantVo>> getUnitClassTypes() {
        List<UnitClassConstantVo> unitClassConstantVoList = unitMetadataService.retrieveExistingUnitClasses();
        return ResponseEntity.ok(unitClassConstantVoList);
    }

    @GetMapping(path = "/levelType", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassLevelTypeVo>> getUnitClassLevels() {
        List<UnitClassLevelTypeVo> unitClassLevelTypeVoList = unitMetadataService.retrieveExistingUnitClassLevelTypes();
        return ResponseEntity.ok(unitClassLevelTypeVoList);
    }

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<UnitClassStatusVo>> getUnitClassStatuses() {
        List<UnitClassStatusVo> unitClassStatusVoList = unitMetadataService.retrieveExistingUnitClassLStatuses();
        return ResponseEntity.ok(unitClassStatusVoList);
    }
}
