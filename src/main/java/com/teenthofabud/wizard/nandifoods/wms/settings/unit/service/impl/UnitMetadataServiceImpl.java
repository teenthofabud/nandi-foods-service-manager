package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitMetadataService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UnitMetadataServiceImpl implements UnitMetadataService {

    @Override
    public List<UnitClassTypeVo> retrieveExistingUnitClassTypes() {
        List<UnitClassTypeVo> unitClassTypeVoList = Arrays.stream(UnitClassType.values()).map(
                e -> UnitClassTypeVo.builder().name(e.name()).build()).toList();
        return unitClassTypeVoList;
    }

    @Override
    public List<UnitClassLevelVo> retrieveExistingUnitClassLevels() {
        List<UnitClassLevelVo> unitClassLevelVoList = Arrays.stream(UnitClassLevelType.values()).map(
                e -> UnitClassLevelVo.builder().level(e.getLevel()).type(e.getType()).build()).toList();
        return unitClassLevelVoList;
    }

    @Override
    public List<UnitClassStatusVo> retrieveExistingUnitClassLStatuses() {
        List<UnitClassStatusVo> unitClassStatusVoList = Arrays.stream(UnitClassStatus.values()).map(
                e -> UnitClassStatusVo.builder().name(e.name()).build()).toList();
        return unitClassStatusVoList;
    }
}
