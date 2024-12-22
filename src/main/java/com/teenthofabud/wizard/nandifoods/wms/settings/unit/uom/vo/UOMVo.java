package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo;

import com.opencsv.bean.CsvBindByName;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassVo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class UOMVo extends UnitClassVo {

    @CsvBindByName(column = "Bulk Code")
    private String bulkCode;

    private Boolean isInventory;

    private Boolean isPurchase;

    private Boolean isSales;

    private Boolean isProduction;

    private List<UOMSelfLinkageVo> selfLinksTo;

}
