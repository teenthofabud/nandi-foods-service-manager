package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import org.javers.core.diff.Diff;

import java.util.Optional;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form);

    public UOMVo retrieveExistingUOMByCode(String code);

    public UOMPageImplVo retrieveAllUOMByLongName(String longName, UOMPageDto uomPageDto);

    public UOMPageImplVo retrieveAllUOMWithinRange(Optional<String> optionalQuery, UOMPageDto uomPageDto);

    public void updateExistingUOMByCode(String code, UOMDto patcheUOMDto);

    //public void updateExistingUOMByCode(String code, UOMDto uomDto);
}
