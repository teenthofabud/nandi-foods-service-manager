package com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.MeasurementSystemAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.JSR385LengthType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.JSR385MassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.JSR385VolumeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditListener.class)
@ToString(callSuper = true)
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public abstract class UnitClassMeasuredValuesEntity implements Auditable {

    @Embedded
    private Audit audit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "length_value", nullable = false)
    @EqualsAndHashCode.Include
    private Double length;

    @Column(name = "length_unit", columnDefinition = "varchar(50)", nullable = false)
    @Type(value = JSR385LengthType.class)
    @EqualsAndHashCode.Include
    private Unit<Length> lengthUnit;

    @Column(name = "width_value", nullable = false)
    @EqualsAndHashCode.Include
    private Double width;

    @Column(name = "width_unit", columnDefinition = "varchar(50)", nullable = false)
    @Type(value = JSR385LengthType.class)
    @EqualsAndHashCode.Include
    private Unit<Length> widthUnit;

    @Column(name = "height_value", nullable = false)
    @EqualsAndHashCode.Include
    private Double height;

    @Column(name = "height_unit", columnDefinition = "varchar(50)", nullable = false)
    @Type(value = JSR385LengthType.class)
    @EqualsAndHashCode.Include
    private Unit<Length> heightUnit;

    @Column(name = "volume_value", nullable = false)
    @EqualsAndHashCode.Include
    private Double volume;

    @Column(name = "volume_unit", columnDefinition = "varchar(50)", nullable = false)
    @Type(value = JSR385VolumeType.class)
    @EqualsAndHashCode.Include
    private Unit<Volume> volumeUnit;

    @Column(name = "weight_value", nullable = false)
    @EqualsAndHashCode.Include
    private Double weight;

    @Column(name = "weight_unit", columnDefinition = "varchar(50)", nullable = false)
    @Type(value = JSR385MassType.class)
    @EqualsAndHashCode.Include
    private Unit<Mass> weightUnit;

    @Column(name = "metric_system", columnDefinition = "varchar(50)", nullable = false)
    @Convert(converter = MeasurementSystemAttributeConverter.class)
    @EqualsAndHashCode.Include
    private MeasurementSystem measurementSystem;

    @Version
    @Column(nullable = false)
    protected Short version;

}
