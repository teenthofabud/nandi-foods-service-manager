package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import com.teenthofabud.wizard.nandifoods.wms.dto.PageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.validator.OptionalEnumValidator;
import com.teenthofabud.wizard.nandifoods.wms.validator.OptionalTypeAttributeValidator;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@Getter
@Setter
public class UOMPageDto extends PageDto {

    @OptionalTypeAttributeValidator(clazz = UOMVo.class)
    @Builder.Default
    private Optional<String> sort = Optional.of("");

    @OptionalEnumValidator(enumClazz = UnitClassStatus.class)
    @Builder.Default
    private Optional<String> status = Optional.of("");

}
