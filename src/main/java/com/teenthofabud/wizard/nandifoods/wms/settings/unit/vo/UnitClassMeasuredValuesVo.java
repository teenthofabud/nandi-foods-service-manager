package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnitClassMeasuredValuesVo {

    @Schema(description = "Measurement system represented by this values set")
    private MeasurementSystem measurementSystem;

    @Schema(example = "1", description = "Measurement system primary key")
    private Long id;

    @Schema(description = "Length value for this UOM according to the configured length unit for this measurement system")
    private Double lengthValue;

    @Schema(example = "cm", description = "Configured default length unit for this measurement system")
    private String lengthUnit;

    @Schema(description = "Width value for this UOM according to the configured width unit for this measurement system")
    private Double widthValue;

    @Schema(example = "cm", description = "Configured default width unit for this measurement system")
    private String widthUnit;

    @Schema(description = "Height value for this UOM according to the configured height unit for this measurement system")
    private Double heightValue;

    @Schema(example = "cm", description = "Configured default height unit for this measurement system")
    private String heightUnit;

    @Schema(description = "Volume value for this UOM according to the configured volume unit for this measurement system")
    private Double volumeValue;

    @Schema(example = "cubic metre", description = "Configured default volume unit for this measurement system")
    private String volumeUnit;

    @CsvBindByName(column = "Weight")
    @CsvBindByPosition(position = 6)
    @Schema(description = "Weight value for this UOM according to the configured weight unit for this measurement system")
    private Double weightValue;

    @Schema(example = "kg", description = "Configured default weight unit for this measurement system")
    private String weightUnit;

}
