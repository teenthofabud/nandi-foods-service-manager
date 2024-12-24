package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.projections;

import com.blazebit.persistence.view.EntityView;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.projections.UnitClassMeasuredValuesSummaryProjection;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.projections.UnitClassSummaryProjection;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.PersistenceCreator;

@EqualsAndHashCode
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UOMSummaryProjection extends UnitClassSummaryProjection {

    private String bulkCode;

    @PersistenceCreator
    public UOMSummaryProjection(UnitClassLevelType levelType, String description, String longName, String shortName, String code, String bulkCode, UnitClassMeasuredValuesSummaryProjection uomMeasuredValue) {
        super(levelType, description, longName, shortName, code, uomMeasuredValue);
        this.bulkCode = bulkCode;
    }
}
