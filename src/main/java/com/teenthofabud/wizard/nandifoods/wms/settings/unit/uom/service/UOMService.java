package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.MeasurementSystemException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.error.UOMException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.error.UOMSelfLinkException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;

import java.util.Optional;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form) throws UOMException, MeasurementSystemException, UOMSelfLinkException;

    public UOMVo retrieveExistingUOMByCode(String code) throws UOMException;

    public void deleteExistingUOMByCode(String code) throws UOMException;

    public void updateExistingUOMByCode(String code, UOMDtoV2 sourceUOMDto) throws UOMException;

    public void approveSavedUOMByCode(String code, Optional<UOMDto> optionallyPatchedUOMDto) throws UOMException;

}
