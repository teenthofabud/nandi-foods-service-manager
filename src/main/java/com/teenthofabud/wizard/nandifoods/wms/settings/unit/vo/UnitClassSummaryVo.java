package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.opencsv.bean.CsvBindByName;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class UnitClassSummaryVo {

    @CsvBindByName(column = "UOM Name")
    protected String type;

    @CsvBindByName(column = "Description")
    protected String description;

    @CsvBindByName(column = "UOM Long Name")
    protected String longName;

    @CsvBindByName(column = "UOM Short Name")
    protected String shortName;

    @CsvBindByName(column = "UOM ID")
    protected String code;

    @CsvBindByName(column = "Weight (KG)")
    protected Double metricWeightValue;

}
