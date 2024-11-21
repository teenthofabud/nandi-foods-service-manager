package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UOMPagedModelVo extends PagedModel<UOMVo> {
    /**
     * Creates a new {@link PagedModel} for the given {@link Page}.
     *
     * @param page must not be {@literal null}.
     */
    public UOMPagedModelVo(Page<UOMVo> page) {
        super(page);
    }
}
