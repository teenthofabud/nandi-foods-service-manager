package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
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

    public UOMPageImplVo retrieveUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto);

    public UOMPageImplVo retrieveUOMWithinRange(Optional<String> optionalQuery, UOMPageDto uomPageDto);

    public void updateExistingUOMByCode(String code, UOMDto patchedUOMDto);

    public void updateExistingUOMByCode(String code, UOMDtoV2 sourceUOMDto);

    public void approveSavedUOMByCode(String code, Optional<UOMDto> optionallyPatchedUOMDto);

    public FileDto downloadUOMAsCSV() throws IOException;

    public FileDto downloadUOMAsPDF() throws IOException;

}
