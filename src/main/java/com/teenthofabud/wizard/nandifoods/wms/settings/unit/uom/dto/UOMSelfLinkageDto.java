package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UOMSelfLinkageDto {

    @JsonSetter(nulls = Nulls.SKIP)
    @Builder.Default
    @JsonProperty("id")
    protected Optional<@Pattern(regexp = "U(100[1-9]|10[1-9][0-9]|1[1-9][0-9]{2}|[2-4][0-9]{3})", message = "id value is invalid") String> code = Optional.empty();

    @JsonSetter(nulls = Nulls.SKIP)
    protected Optional<@NotNull(message = "quantity value is required")
    @Min(value = 1, message = "minimum quantity value is 1") Integer> quantity = Optional.empty();

}
