package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UOMDto extends UnitClassDto {

    @NotNull
    @Min(value = 1)
    @Builder.Default
    private Long bulkCode = 0l;

    @Builder.Default
    private Boolean isInventory = false;

    @Builder.Default
    private Boolean isPurchase = false;

    @Builder.Default
    private Boolean isSales = false;

    @Builder.Default
    private Boolean isProduction = false;

}
