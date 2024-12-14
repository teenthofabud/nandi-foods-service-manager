package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto;

import lombok.*;

import java.util.Optional;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CSVDto {

    private String fileName;

    private byte[] rawContent;

}
