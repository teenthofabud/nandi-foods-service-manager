package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassCrossLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.javers.core.metamodel.annotation.DiffIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMDtoV2 extends UnitClassDtoV2 {

    @NotNull(message = "inventory UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isInventory;

    @NotNull(message = "purchase UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isPurchase;

    @NotNull(message = "sales UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSales;

    @NotNull(message = "production UOM is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isProduction;

//    @Builder.Default
    @ArraySchema(schema = @Schema(
            implementation = UnitClassSelfLinkageForm.class,
            description = "this UOM is linked to these UOMs"))
    @DiffIgnore
    private @Size(min = 1, message = "At least 1 UOM must be linked") List<@Valid UnitClassSelfLinkageDtoV2> linkedUOMs;

    @DiffIgnore
//    @Builder.Default
    private @Size(min = 1, message = "At least 1 PU/HU must be linked") List<@Valid UnitClassCrossLinkageDtoV2> linkedPUHUs;


}
