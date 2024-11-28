package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.BaseUnitClassAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "UOMAPI", description = "UOM Management")
public interface UOMAPI extends BaseUnitClassAPI {

    public static final String BASE_URI = BaseUnitClassAPI.BASE_URI + "/uom";

    @Operation(method = "POST", summary = "UOM creation", description = "postUOM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UOM created")
    })
    public ResponseEntity<Void> postUOM(@RequestBody(description = "UOM form", required = true,
            content = @Content(schema = @Schema(implementation = UOMForm.class))) UOMForm form);

    @Operation(method = "PATCH", summary = "UOM edit", description = "patchUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM edited")
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> patchUOMByCode(String code, @RequestBody(description = "JsonPatch", required = true,
            content = @Content(mediaType = HttpMediaType.APPLICATION_JSON_PATCH,
                    array = @ArraySchema(
                            schema = @Schema(implementation = JsonPatchOperation.class)
                    ))) JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException;


    @Operation(method = "GET", summary = "Get UOM by Id", description = "getUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved UOM by Id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMVo.class))
                    )
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
/*    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved UOM by Id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(
                            schema = @Schema(implementation = UOMVo.class)
                    )))
    })*/
    public ResponseEntity<UOMVo> getUOMByCode(String code);

    @Operation(method = "GET", summary = "Search UOM by query within range", description = "getAllUOMByQueryWithinRange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all UOM matching query within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, required = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, required = true)
    })
    public ResponseEntity<UOMPageImplVo> getAllUOMWithinRange(Integer offset, Long limit);

}
