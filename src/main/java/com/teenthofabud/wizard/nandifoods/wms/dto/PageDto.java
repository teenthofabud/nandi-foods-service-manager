package com.teenthofabud.wizard.nandifoods.wms.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private Optional<@Min(value = 0, message = "Minimum offset is 0") @Max(value = Integer.MAX_VALUE, message = "Minimum offset is " + Integer.MAX_VALUE)Integer> offset = Optional.empty();

    @Builder.Default
    private Optional<@Min(value = 1, message = "Minimum page limit is 1") @Max(value = 1000, message = "Minimum page limit is 1000") Long> limit = Optional.empty();

    @Builder.Default
    private Optional<Boolean> ascending = Optional.of(true);

}
