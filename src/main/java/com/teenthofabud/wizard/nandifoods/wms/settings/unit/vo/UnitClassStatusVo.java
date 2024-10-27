package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UnitClassStatusVo {

    @Schema(example = "HOLD", allowableValues = {"PENDING", "ACTIVE", "HOLD", "CLOSED"}, description = "Name of the Unit Class status")
    private String name;

}
