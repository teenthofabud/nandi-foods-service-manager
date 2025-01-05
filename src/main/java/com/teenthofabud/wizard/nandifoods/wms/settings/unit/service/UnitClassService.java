package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;

import java.io.IOException;
import java.util.Optional;

public interface UnitClassService {

   public UOMPageImplVo retrieveUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto);

    public UOMPageImplVo retrieveUOMWithinRange(Optional<String> optionalQuery, UOMPageDto uomPageDto);

    public FileDto downloadUOMAsCSV() throws IOException;

    public FileDto downloadUOMAsPDF() throws IOException;

}
