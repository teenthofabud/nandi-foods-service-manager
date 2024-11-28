package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UOMPageImplVo extends PageImpl<UOMVo> {
    public UOMPageImplVo(List<UOMVo> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
