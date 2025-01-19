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
public class PaymentTermsForm {

    @NotNull(message = "Payment term code is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "Payment term code", example = "PTEM01")
    private String code;

    @NotNull(message = "Payment term name is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "Payment term name",example = "5 DAYS")
    private String name;

    @NotNull(message = "The number of days until payment is due is required")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED,description = "The number of days until the payment is due",example = "5")
    private Integer daysUntilDue;

}
