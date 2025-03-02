package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassMeasuredValuesEntity;
import com.teenthofabud.wizard.nandifoods.wms.type.IdentifiableCollectionItem;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UOMMeasuredValuesEntity")
@Table(name = "uom_measured_values")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMMeasuredValuesEntity extends UnitClassMeasuredValuesEntity implements IdentifiableCollectionItem<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "uom_measured_values_fk"))
    private UOMEntity uom;

    @Override
    public int compareTo(UnitClassMeasuredValuesEntity o) {
        return Integer.compare(this.getMeasurementSystem().getOrdinal(), o.getMeasurementSystem().getOrdinal());
    }

    @Override
    public Long getID() {
        return this.getId();
    }
}
