package com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants;

import com.teenthofabud.wizard.nandifoods.wms.constants.EnumKeyValue;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import systems.uom.common.USCustomary;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import java.util.List;

import static tech.units.indriya.unit.Units.*;

@FieldNameConstants(onlyExplicitlyIncluded = true)
@Getter
@ToString
public enum MetricSystem implements EnumKeyValue<MetricSystem> {

    @FieldNameConstants.Include SI(MetricPrefix.CENTI(METRE), MetricPrefix.CENTI(METRE), MetricPrefix.CENTI(METRE), CUBIC_METRE, MetricPrefix.KILO(GRAM), 1, "SI", "International System of Units"),
    @FieldNameConstants.Include IMPERIAL(USCustomary.INCH, USCustomary.INCH, USCustomary.INCH, USCustomary.CUBIC_FOOT, USCustomary.POUND, 0, "IMPERIAL", "United States Customary Unit");

    private Unit<Length> widthUnit;
    private Unit<Length> lengthUnit;
    private Unit<Length> heightUnit;
    private Unit<Volume> volumeUnit;
    private Unit<Mass> weightUnit;
    private int ordinal;
    private String name;
    private String description;

    private MetricSystem(Unit<Length> widthUnit, Unit<Length> lengthUnit, Unit<Length> heightUnit, Unit<Volume> volumeUnit, Unit<Mass> weightUnit, int ordinal, String name, String description) {
        this.widthUnit = widthUnit;
        this.lengthUnit = lengthUnit;
        this.heightUnit = heightUnit;
        this.volumeUnit = volumeUnit;
        this.weightUnit = weightUnit;
        this.ordinal = ordinal;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getKey() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.description;
    }

    @Override
    public List<EnumKeyValue<MetricSystem>> getEnumConstants() {
        return List.of(MetricSystem.values());
    }

    @Override
    public String getKeyName() {
        return "name";
    }

    @Override
    public String getValueName() {
        return "description";
    }

    @Override
    public String getTypeName() {
        return MetricSystem.class.getSimpleName();
    }
}
