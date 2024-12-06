package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class UOMPageDtoToPageableConverter implements Converter<UOMPageDto, Pageable> {

    @Value("${wms.settings.uom.search.defaultSort}")
    private String defaultSortProperty;

    @Value("${wms.settings.uom.search.defaultLimit}")
    private Integer defaultLimitProperty;

    @Value("${wms.settings.uom.search.defaultOffset}")
    private Integer defaultOffsetProperty;

    @Override
    public Pageable convert(UOMPageDto source) {
        Sort.Direction direction = source.getAscending().filter(f -> f).map(f -> Sort.Direction.ASC).orElse(Sort.Direction.DESC);
        Sort sort = source.getSort().map(f -> Sort.by(direction, f)).orElse(Sort.by(direction, defaultSortProperty));
        Integer offset = source.getOffset().map(f -> f.intValue()).orElse(defaultOffsetProperty);
        Integer limit = source.getLimit().map(f -> f.intValue()).orElse(defaultLimitProperty);
        Pageable target = PageRequest.of(offset, limit, sort);
        /*Sort.Direction direction = source.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = StringUtils.hasText(source.getSort()) ? Sort.by(direction, source.getSort()) : Sort.by(direction, defaultSortProperty);
        Integer limit = source.getLimit().intValue() < 0 ? defaultLimitProperty : source.getLimit().intValue();
        Pageable target = PageRequest.of(source.getOffset(), limit, sort);*/
        return target;
    }
}
