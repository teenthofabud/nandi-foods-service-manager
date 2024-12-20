package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource;


import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.MetricSystemVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassLevelVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassStatusVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassTypeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "UnitMetadataAPI", description = "Manage Unit Class metadata")
public interface UnitMetadataAPI extends BaseUnitClassAPI {

    public static final String BASE_URI = BaseUnitClassAPI.BASE_URI + "/metadata";

    @Operation(method = "GET", summary = "Get all metric systems and their units", description = "getMetricSystems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Metric Systems and their units",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(
                            schema = @Schema(implementation = MetricSystemVo.class)
                    )))
    })
    public ResponseEntity<List<MetricSystemVo>> getMetricSystems();

    @Operation(method = "GET", summary = "Get all Unit Class types", description = "getUnitClassTypes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Unit Class types",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(
                            schema = @Schema(implementation = UnitClassTypeVo.class)
                    )))
    })
    public ResponseEntity<List<UnitClassTypeVo>> getUnitClassTypes();

    @Operation(method = "GET", summary = "Get all Unit Class levels", description = "getUnitClassLevels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Unit Class levels",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(
                            schema = @Schema(implementation = UnitClassLevelVo.class)
                    )))
    })
    public ResponseEntity<List<UnitClassLevelVo>> getUnitClassLevels();

    @Operation(method = "GET", summary = "Get all Unit Class statuses", description = "getUnitClassStatuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Unit Class statuses",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(
                            schema = @Schema(implementation = UnitClassStatusVo.class)
                    )))
    })
    public ResponseEntity<List<UnitClassStatusVo>> getUnitClassStatuses();



}
