package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.CSVDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;

import java.io.IOException;
import java.util.Optional;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form);

    public UOMVo retrieveExistingUOMByCode(String code);

    public void deleteExistingUOMByCode(String code);

    public UOMPageImplVo retrieveAllUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto);

    public UOMPageImplVo retrieveAllUOMWithinRange(Optional<String> optionalQuery, UOMPageDto uomPageDto);

    public void updateExistingUOMByCode(String code, UOMDto patchedUOMDto);

    public void approveSavedUOMByCode(String code, Optional<UOMDto> optionallyPatchedUOMDto);

    public CSVDto downloadAllUOMInCSV() throws IOException;

}
