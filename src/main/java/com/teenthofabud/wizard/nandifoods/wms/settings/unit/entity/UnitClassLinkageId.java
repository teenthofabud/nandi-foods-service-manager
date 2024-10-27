package com.teenthofabud.wizard.nandifoods.wms.settings.unit.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitClassLinkageId {

    @Column(name = "from_id", nullable = false)
    @EqualsAndHashCode.Include
    private Long fromId;

    @Column(name = "to_id", nullable = false)
    @EqualsAndHashCode.Include
    private Long toId;

}
