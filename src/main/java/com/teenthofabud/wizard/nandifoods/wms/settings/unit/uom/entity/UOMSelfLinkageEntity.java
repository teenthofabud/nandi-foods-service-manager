package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassLinkageId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UOMSelfLinkageEntity")
@Table(name = "uom_self_link")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditListener.class)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Getter
@Setter
public class UOMSelfLinkageEntity implements Auditable {

    @Embedded
    protected Audit audit;

    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    protected UnitClassLinkageId id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    protected Integer quantity;

    @Column(nullable = false)
    @Version
    @EqualsAndHashCode.Include
    @ToString.Include
    protected Short version;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("fromId")
    @JoinColumn(name = "from_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "conversion_from_uom_fk"))
    protected UOMEntity fromUOM;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("toId")
    @JoinColumn(name = "to_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "conversion_to_uom_fk"))
    protected UOMEntity toUOM;

    @Builder
    public UOMSelfLinkageEntity(Integer quantity, UOMEntity fromUOM, UOMEntity toUOM) {
        this.quantity = quantity;
        this.fromUOM = fromUOM;
        this.toUOM = toUOM;
        this.id = UnitClassLinkageId.builder()
                .fromId(fromUOM.getId())
                .toId(toUOM.getId())
                .build();
        this.audit = Audit.builder().build();
    }

    public UOMSelfLinkageEntity setFromUOM(UOMEntity fromUOM) {
        this.fromUOM = fromUOM;
        return this;
    }


    public UOMSelfLinkageEntity setToUOM(UOMEntity toUOM) {
        this.toUOM = toUOM;
        return this;
    }

    public UOMSelfLinkageEntity setFromUOMId(Long fromId) {
        if(ObjectUtils.isEmpty(this.id)) {
            this.id = UnitClassLinkageId.builder().build();
        }
        this.id.setFromId(fromId);
        return this;
    }


    public UOMSelfLinkageEntity setToUOMId(Long toId) {
        if(ObjectUtils.isEmpty(this.id)) {
            this.id = UnitClassLinkageId.builder().build();
        }
        this.id.setToId(toId);
        return this;
    }
}
