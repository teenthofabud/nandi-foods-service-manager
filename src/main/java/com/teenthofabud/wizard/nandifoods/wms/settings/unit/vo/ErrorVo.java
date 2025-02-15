package com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Builder
public class
ErrorVo {

    @Schema(example = "brand is invalid", allowableValues = {"%s is invalid", "Unauthorized access", "No meeting found for %s", "%s is missing", "Service failure: %s"},
            description = "if a specific attribute is causing the issue, the name of the attribute will be used in the message template. Otherwise the template placeholder will " +
                    "be ignored")
    private String message;

    @Schema(example = "ATTRIBUTE_EMPTY", allowableValues = {"ATTRIBUTE_EMPTY", "ATTRIBUTE_INVALID", "ACCESS_DENIED", "MEETING_INVALID", "MEETING_UNAVAILABLE", "ACTION_FAILURE"})
    private String errorCode;

    @Schema(example = "1234-4567-8456", description = "provide this id to ensure efficent error tracing")
    private String traceId;

    private String domain;

    private String subDomain;
}
