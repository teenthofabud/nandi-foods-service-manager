package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;

import java.util.Optional;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form);

    public UOMVo retrieveExistingUOMByCode(String code);

    public void deleteExistingUOMByCode(String code);

    public void updateExistingUOMByCode(String code, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

    public void approveSavedUOMByCode(String code, JsonPatch patch) throws JsonPatchException, JsonProcessingException;

}
