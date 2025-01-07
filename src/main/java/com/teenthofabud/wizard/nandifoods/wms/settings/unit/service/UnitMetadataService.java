package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.*;

import java.util.List;

public interface UnitMetadataService {

    public List<MeasurementSystemVo> retrieveExistingMeasurementSystems();
    public List<UnitClassConstantVo> retrieveExistingUnitClasses();
    public List<UnitClassLevelTypeVo> retrieveExistingUnitClassLevelTypes();
    public List<UnitClassStatusVo> retrieveExistingUnitClassLStatuses();
}
