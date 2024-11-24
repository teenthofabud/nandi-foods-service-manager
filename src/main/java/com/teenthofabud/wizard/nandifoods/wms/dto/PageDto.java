package com.teenthofabud.wizard.nandifoods.wms.dto;

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
public class PageDto {

    @Min(0)
    @Max(Integer.MAX_VALUE)
    @Builder.Default
    private Integer offset = 0;
    @Min(1)
    @Max(1000)
    @Builder.Default
    private Long limit = 1000l;
    @Builder.Default
    private String sort = "";
    @Builder.Default
    private Boolean ascending = true;

}
