package com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditListener.class)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class UnitClassLinkageEntity implements Auditable {

    @Embedded
    protected Audit audit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    protected Long id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    protected Integer quantity;

    @Column(nullable = false)
    @Version
    @EqualsAndHashCode.Include
    protected Short version;

}
