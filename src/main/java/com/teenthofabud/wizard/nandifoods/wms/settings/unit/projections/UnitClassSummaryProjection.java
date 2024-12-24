package com.teenthofabud.wizard.nandifoods.wms.settings.unit.projections;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.PersistenceCreator;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class UnitClassSummaryProjection {

    private UnitClassLevelType levelType;
    private String description;
    private String longName;
    private String shortName;
    private String code;
    private UnitClassMeasuredValuesSummaryProjection uomMeasuredValue;

    @PersistenceCreator
    public UnitClassSummaryProjection(UnitClassLevelType levelType, String description, String longName, String shortName, String code, UnitClassMeasuredValuesSummaryProjection uomMeasuredValue) {
        this.levelType = levelType;
        this.description = description;
        this.longName = longName;
        this.shortName = shortName;
        this.code = code;
        this.uomMeasuredValue = uomMeasuredValue;
    }
}

