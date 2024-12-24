package com.teenthofabud.wizard.nandifoods.wms.settings.unit.projections;

import org.springframework.data.annotation.PersistenceCreator;
import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@Getter
@Setter
public class UnitClassMeasuredValuesSummaryProjection {

    private String weightUnit;
    private Double weightValue;

    @PersistenceCreator
    public UnitClassMeasuredValuesSummaryProjection(String weightUnit, Double weightValue) {
        this.weightUnit = weightUnit;
        this.weightValue = weightValue;
    }
}
