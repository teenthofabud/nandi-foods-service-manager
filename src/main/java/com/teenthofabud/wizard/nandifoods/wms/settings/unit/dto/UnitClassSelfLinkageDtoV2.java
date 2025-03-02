package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassSelfLinkageContract;
import com.teenthofabud.wizard.nandifoods.wms.type.IdentifiableCollectionItem;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javers.core.metamodel.annotation.TypeName;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@TypeName("unitClassSelfLinkage")
public class UnitClassSelfLinkageDtoV2 extends UnitClassLinkageDtoV2 implements UnitClassSelfLinkageContract, IdentifiableCollectionItem<String> {

    @Min(value = 1, message = "minimum quantity value is 1")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "number of UOMs linked to")
    protected Integer quantity;


    @Override
    public String getID() {
        return this.getCode();
    }
}
