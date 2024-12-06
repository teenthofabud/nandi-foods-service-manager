package com.teenthofabud.wizard.nandifoods.wms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
public abstract class PageDto {

    @Builder.Default
    private Optional<@Min(0) @Max(Integer.MAX_VALUE) Integer> offset = Optional.of(0);

    @Builder.Default
    private Optional<@Min(1) @Max(1000) Long> limit = Optional.of(-1l);

    @Builder.Default
    private Optional<Boolean> ascending = Optional.of(true);

}
