package com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(callSuper = true)
@Entity(name = "PUEntity")
@Table(name = "pu",
        indexes = {
                @Index(columnList = "short_name", name = "idx_pu_short_name"),
                @Index(columnList = "long_name", name = "idx_pu_long_name"),
                @Index(columnList = "level", name = "idx_pu_level"),
                @Index(columnList = "type", name = "idx_pu_type"),
                @Index(columnList = "code", name = "idx_pu_code"),
                @Index(columnList = "class", name = "idx_pu_class")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uix_pu_code", columnNames = { "code" })
        }
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class PUEntity extends UnitClassEntity {

        @OneToMany(
                mappedBy = "pu",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        private List<UOMPULinkageEntity> puLinks = new ArrayList<>();


        public void addUOMPULinkage(UOMPULinkageEntity uomPULinkage) {
                this.puLinks.add(uomPULinkage);
        }

        public void removeUOMPULinkage(UOMPULinkageEntity uomPULinkage) {
                this.puLinks.remove(uomPULinkage);
        }
}
