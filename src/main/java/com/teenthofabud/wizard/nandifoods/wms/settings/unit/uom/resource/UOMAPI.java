package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.BaseUnitClassAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.error.UOMException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "UOMAPI", description = "UOM Management")
public interface UOMAPI extends BaseUnitClassAPI {

    public static final String BASE_URI = BaseUnitClassAPI.BASE_URI + "/uom";

    @Operation(method = "POST", summary = "UOM creation", description = "postUOM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UOM created"),
            @ApiResponse(
                    responseCode = "409", description = "UOM already created",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    public ResponseEntity<Void> postUOM(@RequestBody(description = "UOM form", required = true,
            content = @Content(schema = @Schema(implementation = UOMForm.class))) UOMForm form) throws UOMException;



    @Operation(method = "PATCH", summary = "UOM edit", description = "patchUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM edited")
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> patchUOMByCode(String code, @RequestBody(description = "UOM dto", required = true,
            content = @Content(schema = @Schema(implementation = UOMDtoV2.class))) UOMDtoV2 sourceUOMDto) throws JsonPatchException, JsonProcessingException, UOMException;



    @Operation(method = "PATCH", summary = "UOM approval", description = "approveSavedUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM approved"),
            @ApiResponse(
                    responseCode = "409", description = "UOM already approved",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> approveSavedUOMById(String code, @RequestBody(description = "JsonPatch", required = false,
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
    public ResponseEntity<UOMVo> getUOMByCode(String code) throws UOMException;


    @Operation(method = "DELETE", summary = "Delete UOM by Id", description = "deleteUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM edited")
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> deleteUOMById(String code);

}
