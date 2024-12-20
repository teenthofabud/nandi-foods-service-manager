package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
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

    private String metricSystem;

    private Double lengthValue;
    private String lengthUnit;

    private Double widthValue;
    private String widthUnit;

    private Double heightValue;
    private String heightUnit;

    private Double volumeValue;
    private String volumeUnit;

    private Double weightValue;
    private String weightUnit;

}
