package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource;


import com.teenthofabud.wizard.nandifoods.wms.resource.WMSAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.RightsType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@Tag(name = "BaseUnitClassAPI", description = "Manage supported implementations and metadata of Unit Class")
@RolesAllowed({
        RightsType.Fields.VIEW_GLOBAL,
        RightsType.Fields.CREATE_EDIT_GLOBAL
})
public interface BaseUnitClassAPI extends WMSAPI {

    public static final String BASE_URI = "/unit";
    public String getContext();
}
