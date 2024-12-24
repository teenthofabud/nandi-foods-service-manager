package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo;

import com.opencsv.bean.CsvBindByName;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassSummaryVo;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMSummaryVo extends UnitClassSummaryVo {

    @CsvBindByName(column = "Bulk Code")
    private String bulkCode;

}
