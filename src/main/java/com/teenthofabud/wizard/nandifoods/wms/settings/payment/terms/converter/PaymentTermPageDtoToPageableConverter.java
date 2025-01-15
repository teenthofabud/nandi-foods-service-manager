package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.converter;

import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermPageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaymentTermPageDtoToPageableConverter implements Converter<PaymentTermPageDto, Pageable> {

    @Value("${wms.settings.paymentTerms.search.defaultSort}")
    private String defaultSortProperty;

    @Value("${wms.settings.paymentTerms.search.defaultLimit}")
    private Integer defaultLimitProperty;

    @Value("${wms.settings.paymentTerms.search.defaultOffset}")
    private Integer defaultOffsetProperty;

    @Override
    public Pageable convert(PaymentTermPageDto source) {

        Sort.Direction direction = source.getAscending().filter(f -> f).map(f -> Sort.Direction.ASC).orElse(Sort.Direction.DESC);
        Sort sort = source.getSort().map(f -> Sort.by(direction, f)).orElse(Sort.by(direction, defaultSortProperty));
        Integer offset = source.getOffset().map(f -> f.intValue()).orElse(defaultOffsetProperty);
        Integer limit = source.getLimit().map(f -> f.intValue()).orElse(defaultLimitProperty);
        Pageable target = PageRequest.of(offset, limit, sort);
        return target;
    }
}
