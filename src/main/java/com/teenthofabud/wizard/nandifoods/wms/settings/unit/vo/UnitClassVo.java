package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
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
    @CsvBindByName(column = "UOM Name")
    protected String type;

    @CsvBindByName(column = "Description")
    protected String description;

    @CsvBindByName(column = "UOM Long Name")
    protected String longName;

    @CsvBindByName(column = "UOM Short Name")
    protected String shortName;

    @CsvIgnore
    protected List<UnitClassMeasuredValuesVo> measuredValues;

    @JsonProperty("pk")
    @CsvIgnore
    protected Long id;

    @JsonProperty("id")
    @CsvBindByName(column = "UOM ID")
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
