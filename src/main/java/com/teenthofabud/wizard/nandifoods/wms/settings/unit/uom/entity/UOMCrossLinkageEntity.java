package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassLinkageId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Getter
@Setter
public abstract class UOMCrossLinkageEntity implements Auditable {

    @Embedded
    protected Audit audit;

    @EmbeddedId
    @EqualsAndHashCode.Include
    protected UnitClassLinkageId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("fromId")
    @JoinColumn(foreignKey = @ForeignKey(name = "conversion_from_uom_fk"))
    private UOMEntity uom;

    @Column(name = "minimum_quantity", nullable = false)
    @EqualsAndHashCode.Include
    protected Integer minimumQuantity;

    @Column(name = "maximum_quantity", nullable = false)
    @EqualsAndHashCode.Include
    protected Integer maximumQuantity;

    @Version
    @Column(nullable = false)
    protected Short version;

}
