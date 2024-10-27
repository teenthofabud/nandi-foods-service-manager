package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource;


import com.teenthofabud.wizard.nandifoods.wms.settings.constants.RightsType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@Tag(name = "BaseUnitClassAPI", description = "Manage supported implementations and metadata of Unit Class")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400", description = "Invalid UOM data",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
        @ApiResponse(
                responseCode = "401", description = "Not permitted to perform this action",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class))),
        @ApiResponse(
                responseCode = "500", description = "Server failure",
                content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
})
@RolesAllowed({
        RightsType.Fields.VIEW_GLOBAL,
        RightsType.Fields.CREATE_EDIT_GLOBAL
})
public interface BaseUnitClassAPI {

    public static final String BASE_URI = "/unit";
    public String getContext();
}
