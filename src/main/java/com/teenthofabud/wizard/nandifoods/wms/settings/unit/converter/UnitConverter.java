/*
package com.teenthofabud.wizard.nandifoods.wms.inventory.unit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;
import javax.measure.quantity.Mass;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Component
@Slf4j
public class UnitConverter {

    private UnitConfigurationProperties unitConfigurationProperties;

    @Autowired
    public UnitConverter(UnitConfigurationProperties unitConfigurationProperties) {
        this.unitConfigurationProperties = unitConfigurationProperties;
    }

    private Unit<Mass> defaultUnitOfWeight() {
        return Units.getInstance().getUnit(unitConfigurationProperties.defaultUnitOfWeight()).asType(Mass.class);
    }

    private String formatCode(Long id) {
        return String.format(unitConfigurationProperties.defaultCodeFormatPattern(), id);
    }

    private String formatLongName(UnitClassEntity entity) {
        if(StringUtils.hasText(entity.getLongName())) {
            return entity.getLongName();
        } else {
            return String.format(unitConfigurationProperties.defaultLongNameFormat(), entity.getId(), entity.getName(), entity.getDescription());
        }
    }

    private String parseLongName(UnitDto dto) {
        return StringUtils.hasText(dto.getLongName()) ? dto.getLongName() : "";
    }

    private String formatShortName(UnitClassEntity entity) {
        if(StringUtils.hasText(entity.getShortName())) {
            return entity.getShortName();
        } else {
            return String.format(unitConfigurationProperties.defaultShortNameFormat(), entity.getId(), entity.getName());
        }
    }

    private String parseShortName(UnitDto dto) {
        return StringUtils.hasText(dto.getShortName()) ? dto.getShortName() : "";
    }

    @Deprecated
    private Long parseCode(String code) {
        if(StringUtils.hasText(code)) {
            Pattern pattern = Pattern.compile(unitConfigurationProperties.defaultCodeParsePattern());
            Matcher matcher = pattern.matcher(code);
            //matcher.find(); // validator in previous step should make sure that the code confronts to the desired pattern, hence no double checking
            if (matcher.find()) {
                String idStr = matcher.group(1);
                Long id = Long.parseLong(idStr);
                return id;
            } else {
                throw new IllegalArgumentException("UOM Code does not confront to pattern: " + unitConfigurationProperties.defaultCodeParsePattern());
            }
        } else {
            return 0l;
        }
    }

    public UnitClassEntity convert(UnitDto source) {
        UnitClassEntity target = UnitClassEntity.builder()
                .bulkCode(source.getBulkCode())
                .description(source.getDescription())
                .name(source.getName())
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .build();
        log.debug("Converted UnitDto to UnitClassEntity");
        log.trace("Converted {} to {}", source, target);
        return target;
    }

    public UnitDto convert(UnitClassEntity source) {
        UnitDto target = UnitDto.builder()
                .bulkCode(source.getBulkCode())
                .description(source.getDescription())
                .name(source.getName())
                .id(String.valueOf(source.getId()))
                .longName(source.getLongName())
                .shortName(source.getShortName())
                .build();
        log.debug("Converted UnitClassEntity to UnitDto");
        log.trace("Converted {} to {}", source, target);
        return target;
    }
}
*/
