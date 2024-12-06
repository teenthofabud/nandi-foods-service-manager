package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Optional;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UOMSearchDto {

    @Builder.Default
    protected Optional<String> code = Optional.empty();

    @Builder.Default
    private Optional<String> description = Optional.empty();

    @Builder.Default
    private Optional<String> longName = Optional.empty();

    @Builder.Default
    private Optional<String> shortName = Optional.empty();

    @Builder.Default
    private Optional<String> type = Optional.empty();

}
