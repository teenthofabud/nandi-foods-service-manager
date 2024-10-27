package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMCrossLinkageEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@Entity(name = "UOMPULinkageEntity")
@Table(name = "uom_pu_link")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@SuperBuilder
@Getter
@Setter
public class UOMPULinkageEntity extends UOMCrossLinkageEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("toId")
    @JoinColumn(foreignKey = @ForeignKey(name = "conversion_to_pu_fk"))
    private PUEntity pu;

}
