package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource;

import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.UnitException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "UnitClassAPI", description = "Unit Class Reports")
public interface UnitClassAPI extends BaseUnitClassAPI {

    public static final String BASE_URI = BaseUnitClassAPI.BASE_URI;

    @Operation(method = "GET", summary = "Download Unit Class list", description = "downloadUnitClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Downloaded Unit Class list",
                    content = {
                            @Content(mediaType = HttpMediaType.TEXT_CSV),
                            @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)
                    }
            ),
            @ApiResponse(
                    responseCode = "406", description = "Media type not accepted",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    @Parameter(description = "Document type for Unit Class list", name = "Accept", schema = @Schema(implementation = String.class), in = ParameterIn.HEADER, required = true)
    public StreamingResponseBody downloadUnitClass(String accept, HttpServletResponse response) throws IOException, UnitException;

    @Operation(method = "POST", summary = "Upload Unit Class list", description = "uploadUnitClass")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uploaded Unit Class list"),
            @ApiResponse(
                    responseCode = "406", description = "Media type not accepted",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    @Parameter(hidden = true, description = "Document type for Unit Class list file", name = "Content-Type", schema = @Schema(implementation = String.class), in = ParameterIn.HEADER, required = true)
    public StreamingResponseBody uploadUnitClass(String contentType, MultipartFile unitClassList) throws IOException;

    @Operation(method = "GET", summary = "Search Unit Class by long name within range", description = "searchUnitClassByLongName", deprecated = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all Unit Classes matching long name within range",
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
    public ResponseEntity<UOMPageImplVo> searchUnitClassByLongName(String longName, String sort, Boolean ascending, Integer offset, Long limit);

    @Operation(method = "GET", summary = "Search Unit Class by long name", description = "searchUnitClassByLongName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all Unit Classes matching long name within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Long Name", name = "longName", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Sort by", name = "sort", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "UOM Status", name = "status", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Is ascending", name = "ascending", schema = @Schema(implementation = Boolean.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true)
    })
    public ResponseEntity<UOMPageImplVo> searchUnitClassByLongName(String longName, String sort, String status, Boolean ascending, Integer offset, Long limit);


    @Hidden
    @Operation(method = "POST", summary = "Search Unit Class by query parameter within range", description = "searchUOMByQueryParameterWithinRange")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all Unit Classes matching query parameter within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UOMPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Sort by", name = "sort", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Is ascending", name = "ascending", schema = @Schema(implementation = Boolean.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true)
    })
    public ResponseEntity<UOMPageImplVo> searchUnitClassByQueryParameter(@RequestBody(description = "Unit Class search query",
            content = @Content(schema = @Schema(implementation = String.class))) String query, String sort, Boolean ascending, Integer offset, Long limit);

}
