package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UOMSelfLinkageEntity")
@Table(name = "uom_self_link")
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditListener.class)
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Getter
@Setter
public class UOMSelfLinkageEntity implements Auditable {

    @Embedded
    protected Audit audit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "from_id", foreignKey = @ForeignKey(name = "conversion_from_uom_fk"))
    protected UOMEntity fromUom;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "to_id",  foreignKey = @ForeignKey(name = "conversion_to_uom_fk"))
    protected UOMEntity toUom;

}
