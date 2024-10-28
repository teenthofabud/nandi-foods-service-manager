package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class UnitClassVo {

    protected String level;

    protected String name;

    protected String description;

    protected String longName;

    protected String shortName;

    protected UnitClassMeasuredValuesVo metric;

    protected UnitClassMeasuredValuesVo imperial;

    @JsonProperty("pk")
    protected Long id;

    @JsonProperty("id")
    protected String code;

    protected String status;

    protected String dateCreated;

    protected String effectiveDate;

    protected String modifiedDate;

    protected String lastUpdated;

    protected String updatedBy;

}
