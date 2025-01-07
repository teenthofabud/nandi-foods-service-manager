package com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity;

import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.audit.AuditListener;
import com.teenthofabud.wizard.nandifoods.wms.audit.Auditable;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassLevelAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassTypeAttributeConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassStatusAttributeConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

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

    @Column(name = "approval_time", nullable = false)
    @EqualsAndHashCode.Include
    protected LocalDate effectiveDate;

    @Column(name = "class", nullable = false)
    @Convert(converter = UnitClassAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClass _class;

    @Column(name = "level", nullable = false)
    @Convert(converter = UnitClassLevelAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClassLevel level;

    @Column(name = "type", nullable = false)
    @Convert(converter = UnitClassTypeAttributeConverter.class)
    @EqualsAndHashCode.Include
    protected UnitClassType type;

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
