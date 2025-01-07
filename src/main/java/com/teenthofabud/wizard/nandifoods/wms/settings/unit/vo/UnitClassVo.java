package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevel;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @JsonProperty("class")
    @Schema(description = "UOM Class")
    protected UnitClass _class;

    @Schema(description = "UOM Level")
    protected UnitClassLevel level;

    @JsonProperty("name")
    @CsvBindByName(column = "UOM Name")
    @CsvBindByPosition(position = 2)
    @Schema(description = "UOM Type")
    protected UnitClassType type;

    @CsvBindByName(column = "Description")
    @CsvBindByPosition(position = 3)
    @Schema(example = "1 X 4LB", description = "Description of the UOM")
    protected String description;

    @CsvBindByName(column = "UOM Long Name")
    @CsvBindByPosition(position = 4)
    @Schema(example = "U1009 EACH (3 X 4LB)", description = "[UOM ID] [UOM Type] (Description)")
    protected String longName;

    @CsvBindByName(column = "UOM Short Name")
    @CsvBindByPosition(position = 5)
    @Schema(example = "EACH (U1009)", description = "[UOM Type] (UOM ID)")
    protected String shortName;

    @CsvIgnore
    @ArraySchema(schema = @Schema(
            implementation = UnitClassMeasuredValuesVo.class,
            description = "measured values in supported measurement systems"))
    protected List<UnitClassMeasuredValuesVo> measuredValues;

    @JsonProperty("pk")
    @CsvIgnore
    @Schema(description = "UOM primary key")
    protected Long id;

    @JsonProperty("id")
    @CsvBindByName(column = "UOM ID")
    @CsvBindByPosition(position = 1)
    @Schema(description = "UOM ID")
    protected String code;

    @Schema(description = "UOM Status")
    protected UnitClassStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(title = "yyy-MM-dd", description = "UOM Created On Date")
    protected LocalDate dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(title = "yyy-MM-dd", description = "UOM Approved On Date")
    protected LocalDate effectiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(title = "yyy-MM-dd", description = "UOM Last Modified On Date")
    protected Optional<LocalDate> modifiedDate;

    @Schema(example = "username", description = "UOM Last Modified By user")
    protected String updatedBy;

}
