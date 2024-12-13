package com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassLevelType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassLevelTypeAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassStatusAttributeConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditListener.class)
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
public abstract class UnitClassEntity implements Auditable {

    // Needed as multiple mapped class without @Id is an anti pattern in hibernate, evaluated by anirban from debugging hibernate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected Long id;

    @Embedded
    private Audit audit;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    protected String code;

    @Column(name = "class_type", nullable = false)
    @Convert(converter = UnitClassAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClassType type;

    @Column(name = "level_type", nullable = false)
    @Convert(converter = UnitClassLevelTypeAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClassLevelType levelType;

    @Column(nullable = false)
    @Convert(converter = UnitClassStatusAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClassStatus status;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    protected String description;

    @Column(name = "long_name", nullable = false)
    @EqualsAndHashCode.Include
    protected String longName;

    @Column(name = "short_name", nullable = false)
    @EqualsAndHashCode.Include
    protected String shortName;

    @Version
    @Column(nullable = false)
    protected Short version;

}
