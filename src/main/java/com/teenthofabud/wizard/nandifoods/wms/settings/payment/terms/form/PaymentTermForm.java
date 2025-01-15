package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form;

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
public class PaymentTermForm {

    @NotNull(message = "Payment term code is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotNull(message = "Payment term name is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "The number of days until payment is due (daysUntilDue) is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer daysUntilDue;

}
