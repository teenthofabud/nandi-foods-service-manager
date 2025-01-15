package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentTermDto {

    @JsonIgnore
    private String code;

    @JsonIgnore
    private String name;

    @NotNull(message = "The number of days until payment is due (daysUntilDue) is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer daysUntilDue;

}
