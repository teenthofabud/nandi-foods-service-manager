package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassMeasuredValuesEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity(name = "PUMeasuredValuesEntity")
@Table(name = "pu_measured_values")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Getter
@Setter
public class PUMeasuredValuesEntity extends UnitClassMeasuredValuesEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "pu_measured_values_fk"))
    private PUEntity pu;

}
