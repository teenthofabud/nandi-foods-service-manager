package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UnitClassLevelTypeVo {

    @Schema(example = "Level 1", description = "Name of the Unit Class level")
    private String level;

    @Schema(example = "Each", description = "Name of the Unit Class type")
    private String type;

}
