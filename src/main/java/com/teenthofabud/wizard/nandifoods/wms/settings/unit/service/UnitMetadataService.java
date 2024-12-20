package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.MetricSystemVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassTypeVo;

import java.util.List;

public interface UnitMetadataService {

    public List<MetricSystemVo> retrieveExistingMetricSystems();
    public List<UnitClassTypeVo> retrieveExistingUnitClassTypes();
    public List<UnitClassLevelVo> retrieveExistingUnitClassLevels();
    public List<UnitClassStatusVo> retrieveExistingUnitClassLStatuses();
}
