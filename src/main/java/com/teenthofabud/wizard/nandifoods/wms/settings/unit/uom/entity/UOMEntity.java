package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.UOMHULinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.UOMPULinkageEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "UOMEntity")
@DynamicUpdate
@Table(name = "uom",
        indexes = {
                @Index(columnList = "short_name", name = "idx_uom_short_name"),
                @Index(columnList = "long_name", name = "idx_uom_long_name"),
                @Index(columnList = "level_type", name = "idx_uom_level_type"),
                @Index(columnList = "code", name = "idx_uom_code"),
                @Index(columnList = "class_type", name = "idx_uom_class_type")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uix_uom_code", columnNames = { "code" })
        }
)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UOMEntity extends UnitClassEntity {


    @Column(name = "bulk_code", nullable = false)
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    @ToString.Include
    private String bulkCode;

    @Column(name = "is_inventory", nullable = false)
    @ColumnDefault("true")
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    @ToString.Include
    private Boolean isInventory;

    @Column(name = "is_purchase")
    @ColumnDefault("false")
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    @ToString.Include
    private Boolean isPurchase;

    @Column(name = "is_sales")
    @ColumnDefault("true")
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    @ToString.Include
    private Boolean isSales;

    @Column(name = "is_production")
    @ColumnDefault("true")
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    @ToString.Include
    private Boolean isProduction;

    @OneToMany(
            mappedBy = "fromUOM",
            cascade = { CascadeType.DETACH },
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @Getter
    @ToString.Include
    private List<UOMSelfLinkageEntity> fromUOMs;

    @OneToMany(
            mappedBy = "toUOM",
            cascade = { CascadeType.DETACH },
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @Getter
    @ToString.Include
    private List<UOMSelfLinkageEntity> toUOMs;

    @OneToMany(
            mappedBy = "uom",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH },
            orphanRemoval = true
    )
    @Getter
    @ToString.Include
    private List<UOMHULinkageEntity> huLinks;

    @OneToMany(
            mappedBy = "uom",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH },
            orphanRemoval = true
    )
    @Getter
    @ToString.Include
    private List<UOMPULinkageEntity> puLinks;

    @OneToMany(
            mappedBy = "uom",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @Getter
    @ToString.Include
    protected List<UOMMeasuredValuesEntity> uomMeasuredValues;

    public UOMMeasuredValuesEntity addUOMeasuredValue(UOMMeasuredValuesEntity uomMeasuredValue) {
        if(ObjectUtils.isEmpty(this.uomMeasuredValues)) {
            this.uomMeasuredValues = new CopyOnWriteArrayList<>();
        }
        this.uomMeasuredValues.add(uomMeasuredValue);
        return uomMeasuredValue;
    }

    public void removeUOMMeasuredValue(UOMMeasuredValuesEntity uomMeasuredValue) {
        if(!ObjectUtils.isEmpty(this.uomMeasuredValues)) {
            this.uomMeasuredValues.remove(uomMeasuredValue);
        }
    }

    public UOMSelfLinkageEntity addConversionToUOM(UOMSelfLinkageEntity to) {
        if(ObjectUtils.isEmpty(this.toUOMs)) {
            this.toUOMs = new CopyOnWriteArrayList<>();
        }
        this.toUOMs.add(to);
        return to;
    }

    public void removeConversionToUOM(UOMSelfLinkageEntity to) {
        if(!ObjectUtils.isEmpty(this.toUOMs)) {
            this.toUOMs.remove(to);
        }
    }

    public void removeConversionToUOM() {
        if(!ObjectUtils.isEmpty(this.toUOMs)) {
            this.toUOMs.forEach(f -> f.setToUOM()); // SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
            this.toUOMs.clear();
        }
    }

    public UOMSelfLinkageEntity addConversionFromUOM(UOMSelfLinkageEntity from) {
        if(ObjectUtils.isEmpty(this.fromUOMs)) {
            this.fromUOMs = new CopyOnWriteArrayList<>();
        }
        this.fromUOMs.add(from);
        return from;
    }

    public void removeConversionFromUOM(UOMSelfLinkageEntity from) {
        if(!ObjectUtils.isEmpty(this.fromUOMs)) {
            this.fromUOMs.remove(from);
        }
    }

    public void removeConversionFromUOM() {
        if(!ObjectUtils.isEmpty(this.fromUOMs)) {
            this.fromUOMs.forEach(f -> f.setFromUOM()); // SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
            this.fromUOMs.clear();
        }
    }

    public UOMHULinkageEntity addUOMHULinkage(UOMHULinkageEntity uomHULinkage) {
        if(ObjectUtils.isEmpty(this.huLinks)) {
            this.huLinks = new CopyOnWriteArrayList<>();
        }
        this.huLinks.add(uomHULinkage);
        return uomHULinkage;
    }

    public void removeUOMHULinkage(UOMHULinkageEntity uomHULinkage) {
        if(!ObjectUtils.isEmpty(this.huLinks)) {
            this.huLinks.remove(uomHULinkage);
        }
    }

    public void removeUOMHULinkage() {
        if(!ObjectUtils.isEmpty(this.huLinks)) {
            this.huLinks.clear();
        }
    }

    public UOMPULinkageEntity addUOMPULinkage(UOMPULinkageEntity uomPULinkage) {
        if(ObjectUtils.isEmpty(this.puLinks)) {
            this.puLinks = new CopyOnWriteArrayList<>();
        }
        this.puLinks.add(uomPULinkage);
        return uomPULinkage;
    }

    public void removeUOMPULinkage(UOMPULinkageEntity uomPULinkage) {
        if(!ObjectUtils.isEmpty(this.puLinks)) {
            this.puLinks.remove(uomPULinkage);
        }
    }

    public void removeUOMPULinkage() {
        if(!ObjectUtils.isEmpty(this.puLinks)) {
            this.puLinks.clear();
        }
    }

}
