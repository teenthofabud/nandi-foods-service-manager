package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetricSystemVo {

    @Schema(example = "SI", description = "Name of the metric system")
    private String name;

    @Schema(example = "cm", description = "Name of the unit to measure width")
    private String widthUnit;

    @Schema(example = "cm", description = "Name of the unit to measure length")
    private String lengthUnit;

    @Schema(example = "cubic metre", description = "NName of the unit to measure volume")
    private String volumeUnit;

    @Schema(example = "kg", description = "Name of the unit to measure weight")
    private String weightUnit;

}
