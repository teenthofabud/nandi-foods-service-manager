package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.UnitException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;

import java.util.Optional;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form) throws UnitException;

    public UOMVo retrieveExistingUOMByCode(String code) throws UnitException;

    public void deleteExistingUOMByCode(String code);

    public void updateExistingUOMByCode(String code, UOMDtoV2 sourceUOMDto) throws UnitException;

    public void approveSavedUOMByCode(String code, Optional<UOMDto> optionallyPatchedUOMDto);

}
