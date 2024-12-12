package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
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
@NoArgsConstructor
@Getter
@Setter
public class UOMDto extends UnitClassDto {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<Boolean> isInventory = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<Boolean> isPurchase = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<Boolean> isSales = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<Boolean> isProduction = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    private Optional<List<@Valid UOMSelfLinkageDto>> linkedUOMs = Optional.of(new ArrayList<>());

    /*public UOMDto() {
        super();
        this.isInventory = Optional.ofNullable(null);
        this.isPurchase = Optional.ofNullable(null);
        this.isSales = Optional.ofNullable(null);
        this.isProduction = Optional.ofNullable(null);
        this.linkedUOMs = Optional.of(new ArrayList<>());
    }*/
}
