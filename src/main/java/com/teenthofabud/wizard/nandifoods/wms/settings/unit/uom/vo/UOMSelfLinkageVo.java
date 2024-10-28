package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLinkVo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UOMSelfLinkageVo extends UnitClassLinkVo {

    private UOMVo to;

}
