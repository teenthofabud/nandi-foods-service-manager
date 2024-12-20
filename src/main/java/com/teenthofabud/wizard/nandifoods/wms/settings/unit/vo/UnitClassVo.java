package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class UnitClassVo {

    protected String level;

    protected String name;

    protected String description;

    protected String longName;

    protected String shortName;

    protected List<UnitClassMeasuredValuesVo> measuredValues;

    @JsonProperty("pk")
    protected Long id;

    @JsonProperty("id")
    @CsvBindByName(column = "id")
    protected String code;

    protected String status;

    protected String dateCreated;

    protected String effectiveDate;

    protected String modifiedDate;

    protected String lastUpdated;

    protected String updatedBy;

}
