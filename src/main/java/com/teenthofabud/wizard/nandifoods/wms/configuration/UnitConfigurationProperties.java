package com.teenthofabud.wizard.nandifoods.wms.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public record UnitConfigurationProperties(
        String defaultUnitOfWeight,
        String defaultCodeFormatPattern, // U%d
        String defaultCodeParsePattern, // U(\\d+),
        String defaultLongNameFormat, // // U%d %s (%s)
        String defaultShortNameFormat, // // U%d %s
    Boolean enableCodeFormattingInVo,
    String currentVoCodeFormattingStrategy,
    String voCodeFormatPrefix,
    String voCodeFormatPattern,
    String voCodeParsePattern,
    Boolean enableDefaultLongName,
    String currentLongNameStrategy,
    Boolean enableDefaultShortName,
    String currentShortNameStrategy,
    Boolean enableWeightFormattingInVo,
    Boolean includeUnitTypeInVoWeight,
    Boolean voWeightFormatPattern,
    Boolean currentVoWeightFormattingStrategy
) {
}
