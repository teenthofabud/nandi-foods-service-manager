package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassDto;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@Getter
@Setter
public class UOMDto extends UnitClassDto {

    private Optional<Boolean> isInventory;

    private Optional<Boolean> isPurchase;

    private Optional<Boolean> isSales;

    private Optional<Boolean> isProduction;

    private Optional<List<@Valid UOMSelfLinkageDto>> linkedUOMs;

    public UOMDto() {
        super();
        this.isInventory = Optional.empty();
        this.isPurchase = Optional.empty();
        this.isSales = Optional.empty();
        this.isProduction = Optional.empty();
        this.linkedUOMs = Optional.of(new ArrayList<>());
    }
}
