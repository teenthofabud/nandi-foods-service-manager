package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.vo;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassVo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class HUVo extends UnitClassVo {

    private Boolean isFlexHu;

}
