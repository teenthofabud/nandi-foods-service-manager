package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.teenthofabud.wizard.nandifoods.wms.dto.PageDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class UOMPageDto extends PageDto {

    // Validate against implementing type dto
    @Builder.Default
    private Optional<String> sort = Optional.of("");

}
