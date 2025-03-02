package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity.UnitClassLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.type.IdentifiableCollectionItem;
import jakarta.persistence.*;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import tech.uom.lib.common.function.Identifiable;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "UOMSelfLinkageEntity")
@Table(name = "uom_self_link")
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@Getter
@Setter
public class UOMSelfLinkageEntity extends UnitClassLinkageEntity implements IdentifiableCollectionItem<String> {

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "from_id", foreignKey = @ForeignKey(name = "conversion_from_uom_fk"))
//    @ToString.Include
    protected UOMEntity fromUom;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "to_id",  foreignKey = @ForeignKey(name = "conversion_to_uom_fk"))
//    @ToString.Include
    protected UOMEntity toUom;

    // Using toUOM's code as the identifier for comparison as fromUOM's code will be same;
    // This applies to the only case when comparitively updating a specific UOM's selfLinkages in case of update
    @Override
    public String getID() {
        return toUom.getCode();
    }
}
