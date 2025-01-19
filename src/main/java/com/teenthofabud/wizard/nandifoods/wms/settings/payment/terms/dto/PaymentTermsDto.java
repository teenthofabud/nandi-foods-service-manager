package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javers.core.metamodel.annotation.DiffIgnore;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentTermsDto {

    @JsonIgnore
    @DiffIgnore
    private String code;

    @NotNull(message = "Payment term name is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "The number of days until payment is due is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer daysUntilDue;

}
