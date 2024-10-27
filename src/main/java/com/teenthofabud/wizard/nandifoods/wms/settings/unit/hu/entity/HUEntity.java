package com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@Entity(name = "HUEntity")
@Table(name = "hu",
        indexes = {
                @Index(columnList = "short_name", name = "idx_hu_short_name"),
                @Index(columnList = "long_name", name = "idx_hu_long_name"),
                @Index(columnList = "level_type", name = "idx_hu_level_type"),
                @Index(columnList = "code", name = "idx_hu_code"),
                @Index(columnList = "class_type", name = "idx_hu_class_type")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uix_hu_code", columnNames = { "code" })
        }
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class HUEntity extends UnitClassEntity {

    @Column(name = "is_flex_hu", nullable = false)
    @ColumnDefault("true")
    @EqualsAndHashCode.Include
    @Getter
    @Setter
    private Boolean isFlexHu;

    @OneToMany(
            mappedBy = "hu",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    protected List<HUMeasuredValuesEntity> huMeasuredValues = new ArrayList<>();

    public void addHUMeasuredValue(HUMeasuredValuesEntity huMeasuredValue) {
        this.huMeasuredValues.add(huMeasuredValue);
    }

    public void removeHUMeasuredValue(HUMeasuredValuesEntity huMeasuredValue) {
        this.huMeasuredValues.remove(huMeasuredValue);
    }

    @OneToMany(
            mappedBy = "hu",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UOMHULinkageEntity> huLinks = new ArrayList<>();


    public void addUOMHULinkage(UOMHULinkageEntity uomHULinkage) {
        this.huLinks.add(uomHULinkage);
    }

    public void removeUOMHULinkage(UOMHULinkageEntity uomHULinkage) {
        this.huLinks.remove(uomHULinkage);
    }

}
