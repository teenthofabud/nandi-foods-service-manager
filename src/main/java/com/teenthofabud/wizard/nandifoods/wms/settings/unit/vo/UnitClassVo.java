package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @JsonProperty("name")
    protected String type;

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

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate effectiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Optional<LocalDate> modifiedDate;

    protected LocalDate lastUpdated;

    protected String updatedBy;

}
