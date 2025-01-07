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
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementSystemVo {

    @Schema(example = "SI", description = "Name of the measurement system")
    private String name;

    @Schema(example = "Internation System of Measurements", description = "Description of the measurement system")
    private String description;

    @Schema(example = "cm", description = "Name of the unit to measure width")
    private String widthUnit;

    @Schema(example = "cm", description = "Name of the unit to measure length")
    private String lengthUnit;

    @Schema(example = "cm", description = "Name of the unit to measure height")
    private String heightUnit;

    @Schema(example = "cubic metre", description = "NName of the unit to measure volume")
    private String volumeUnit;

    @Schema(example = "kg", description = "Name of the unit to measure weight")
    private String weightUnit;

}
