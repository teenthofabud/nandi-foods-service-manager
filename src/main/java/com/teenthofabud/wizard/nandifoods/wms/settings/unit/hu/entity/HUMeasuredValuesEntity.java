package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassMeasuredValuesEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString
@Entity(name = "HUMeasuredValuesEntity")
@Table(name = "hu_measured_values")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@Setter
public class HUMeasuredValuesEntity extends UnitClassMeasuredValuesEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "hu_measured_values_fk"))
    private HUEntity hu;

    @Override
    public int compareTo(UnitClassMeasuredValuesEntity o) {
        return Integer.compare(this.getMeasurementSystem().getOrdinal(), o.getMeasurementSystem().getOrdinal());
    }

}
