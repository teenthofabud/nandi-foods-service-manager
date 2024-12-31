package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PUPageImplVo extends PageImpl<UOMVo> {
    public PUPageImplVo(List<UOMVo> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
