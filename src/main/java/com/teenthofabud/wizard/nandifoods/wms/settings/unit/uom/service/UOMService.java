package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service;

import com.teenthofabud.wizard.nandifoods.wms.dto.PageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import org.javers.core.diff.Diff;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UOMService {

    public UOMVo createNewUOM(UOMForm form);

    public UOMVo retrieveExistingUOMByCode(String code);

    public List<UOMVo> retrieveAllUOMWithinRange(PageDto pageDto);

    public UOMVo updateExistingUOMByCode(String code, Diff dtoUpdates);
}
