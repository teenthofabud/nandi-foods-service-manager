package com.teenthofabud.wizard.nandifoods.wms.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    @Min(0)
    @Max(Integer.MAX_VALUE)
    @Builder.Default
    private Integer offset = 0;
    @Min(1)
    @Max(Integer.MAX_VALUE)
    @Builder.Default
    private Integer total = Integer.MAX_VALUE;
    @Builder.Default
    private String sort = "";
    @Builder.Default
    private Boolean ascending = true;

}
