package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentTermVo {

    @Schema(description = "Payment term primary key")
    private Long id;

    @Schema(description = "Payment term code")
    private String code;

    @Schema(description = "Payment term name")
    private String name;

    @Schema(description = "The number of days until the payment is due")
    private Integer daysUntilDue;
}
