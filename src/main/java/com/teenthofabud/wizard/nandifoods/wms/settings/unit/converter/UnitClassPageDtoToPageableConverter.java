package com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter;

import com.teenthofabud.wizard.nandifoods.wms.dto.PageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UnitClassPageDtoToPageableConverter implements Converter<PageDto, Pageable> {

    @Override
    public Pageable convert(PageDto source) {
        Sort.Direction direction = source.getAscending() ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = StringUtils.hasText(source.getSort()) ? Sort.by(direction, source.getSort()) : Sort.by(direction, "code");
        Pageable target = PageRequest.of(source.getOffset(), source.getSize(), sort);
        return target;
    }
}
