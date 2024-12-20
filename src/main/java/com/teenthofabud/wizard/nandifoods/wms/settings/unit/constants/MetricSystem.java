package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import systems.uom.common.USCustomary;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import static tech.units.indriya.unit.Units.*;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
@ToString
public enum MetricSystem {

    @FieldNameConstants.Include SI(MetricPrefix.CENTI(METRE), MetricPrefix.CENTI(METRE), MetricPrefix.CENTI(METRE), CUBIC_METRE, MetricPrefix.KILO(GRAM)),
    @FieldNameConstants.Include IMPERIAL(USCustomary.INCH, USCustomary.INCH, USCustomary.INCH, USCustomary.CUBIC_FOOT, USCustomary.POUND);

    private Unit<Length> widthUnit;
    private Unit<Length> lengthUnit;
    private Unit<Length> heightUnit;
    private Unit<Volume> volumeUnit;
    private Unit<Mass> weightUnit;

    private MetricSystem(Unit<Length> widthUnit, Unit<Length> lengthUnit, Unit<Length> heightUnit, Unit<Volume> volumeUnit, Unit<Mass> weightUnit) {
        this.widthUnit = widthUnit;
        this.lengthUnit = lengthUnit;
        this.heightUnit = heightUnit;
        this.volumeUnit = volumeUnit;
        this.weightUnit = weightUnit;
    }
}
