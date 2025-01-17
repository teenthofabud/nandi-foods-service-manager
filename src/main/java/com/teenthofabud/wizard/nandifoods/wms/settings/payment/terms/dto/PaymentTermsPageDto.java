package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto;

import com.teenthofabud.wizard.nandifoods.wms.dto.PageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;
import com.teenthofabud.wizard.nandifoods.wms.validator.OptionalTypeAttributeValidator;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class PaymentTermsPageDto extends PageDto {



    @OptionalTypeAttributeValidator(clazz = PaymentTermsVo.class)
    @Builder.Default
    private Optional<String> sort = Optional.empty();
}
