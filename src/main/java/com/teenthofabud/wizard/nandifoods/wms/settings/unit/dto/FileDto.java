package com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto;

import lombok.*;

import java.io.InputStream;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileDto {

    private String fileName;

    //private byte[] rawContent;

    private InputStream rawContent;

}
