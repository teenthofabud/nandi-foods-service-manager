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
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import io.swagger.v3.oas.annotations.Hidden;
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
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

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
            content = @Content(schema = @Schema(implementation = UOMForm.class))) UOMForm form);

    @Operation(method = "PATCH", summary = "UOM edit", description = "patchUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM edited")
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> patchUOMByCode(String code, @RequestBody(description = "JsonPatch", required = false,
            content = @Content(mediaType = HttpMediaType.APPLICATION_JSON_PATCH,
                    array = @ArraySchema(
                            schema = @Schema(implementation = JsonPatchOperation.class)
                    ))) JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException;

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
    public ResponseEntity<UOMVo> getUOMByCode(String code);

    @Operation(method = "GET", summary = "Download UOM List", description = "downloadAllUOM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Downloaded UOM List",
                    content = {
                            @Content(mediaType = HttpMediaType.TEXT_CSV),
                            @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)
                    }
            )
    })
    public StreamingResponseBody downloadAllUOM(String accept, HttpServletResponse response) throws IOException;

    @Operation(method = "DELETE", summary = "Delete UOM by Id", description = "deleteUOMById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "UOM edited")
    })
    @Parameter(description = "UOM Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> deleteUOMById(String code);

    @Operation(method = "GET", summary = "Search UOM by long name within range", description = "searchAllUOMByLongNameWithinRange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all UOM matching long name within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Long Name", name = "longName", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Sort by", name = "sort", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Is ascending", name = "ascending", schema = @Schema(implementation = Boolean.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true)
    })
    public ResponseEntity<UOMPageImplVo> searchAllUOMByLongNameWithinRange(String longName, String sort, Boolean ascending, Integer offset, Long limit);


    @Hidden
    @Operation(method = "POST", summary = "Search UOM by query parameter within range", description = "searchAllUOMByQueryParameterWithinRange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all UOM matching query parameter within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Sort by", name = "sort", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Is ascending", name = "ascending", schema = @Schema(implementation = Boolean.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true)
    })
    public ResponseEntity<UOMPageImplVo> searchAllUOMByQueryParameterWithinRange(@RequestBody(description = "UOM search query",
            content = @Content(schema = @Schema(implementation = String.class))) String query, String sort, Boolean ascending, Integer offset, Long limit);

}
