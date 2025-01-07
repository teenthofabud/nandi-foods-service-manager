package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassMeasuredValuesEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
@Entity(name = "UOMMeasuredValuesEntity")
@Table(name = "uom_measured_values")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMMeasuredValuesEntity extends UnitClassMeasuredValuesEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "uom_measured_values_fk"))
    private UOMEntity uom;

}
