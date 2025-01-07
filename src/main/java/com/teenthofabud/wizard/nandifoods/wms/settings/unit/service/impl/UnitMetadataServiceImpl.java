package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitMetadataService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.MeasurementSystemVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelTypeVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassConstantVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UnitMetadataServiceImpl implements UnitMetadataService {

    @Override
    public List<MeasurementSystemVo> retrieveExistingMeasurementSystems() {
        List<MeasurementSystemVo> measurementSystemVoList = Arrays.stream(MeasurementSystem.values()).map(
                e -> MeasurementSystemVo.builder()
                        .name(e.name())
                        .lengthUnit(e.getLengthUnit().toString())
                        .widthUnit(e.getWidthUnit().toString())
                        .weightUnit(e.getWeightUnit().toString())
                        .volumeUnit(e.getVolumeUnit().toString())
                        .heightUnit(e.getHeightUnit().toString())
                        .description(e.getDescription())
                        .build()).toList();
        return measurementSystemVoList;
    }

    @Override
    public List<UnitClassConstantVo> retrieveExistingUnitClasses() {
        List<UnitClassConstantVo> unitClassConstantVoList = Arrays.stream(UnitClass.values()).map(
                e -> UnitClassConstantVo.builder()._class(e).build()).toList();
        return unitClassConstantVoList;
    }

    @Override
    public List<UnitClassLevelTypeVo> retrieveExistingUnitClassLevelTypes() {
        List<UnitClassLevelTypeVo> unitClassLevelTypeVoList = Arrays.stream(UnitClassLevelType.values()).map(
                e -> UnitClassLevelTypeVo.builder().level(e.getLevel().getName()).type(e.getType().getName()).build()).toList();
        return unitClassLevelTypeVoList;
    }

    @Override
    public List<UnitClassStatusVo> retrieveExistingUnitClassLStatuses() {
        List<UnitClassStatusVo> unitClassStatusVoList = Arrays.stream(UnitClassStatus.values()).map(
                e -> UnitClassStatusVo.builder().name(e.name()).build()).toList();
        return unitClassStatusVoList;
    }
}
