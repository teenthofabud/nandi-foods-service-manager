package com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.resource.WMSAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.RightsType;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.dto.PaymentTermsDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.error.PaymentTermsException;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.form.PaymentTermsForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.payment.terms.vo.PaymentTermsVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
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
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "PaymentTermsAPI", description = "Payment Terms Management")
@RolesAllowed({
        RightsType.Fields.VIEW_GLOBAL,
        RightsType.Fields.CREATE_EDIT_GLOBAL
})
public interface PaymentTermsAPI extends WMSAPI {

    public static final String BASE_URI = "/payment-terms";


    @Operation(method = "POST", summary = "Payment Terms creation", description = "postPaymentTerms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment Terms created"),
            @ApiResponse(
                    responseCode = "409", description = "Payment Terms already exists",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorVo.class)))
    })
    public ResponseEntity<Void> postPaymentTerms(@RequestBody(description = "Payment Terms form", required = true,
            content = @Content(schema = @Schema(implementation = PaymentTermsForm.class))) PaymentTermsForm form) throws PaymentTermsException;


    @Operation(method = "PATCH", summary = "Payment Terms edit", description = "patchPaymentTermsById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment Terms edited"),

    })
    @Parameter(description = "Payment Terms Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> patchPaymentTermsByCode(String code, @RequestBody(description = "Payment Terms dto", required = true,
            content = @Content(schema = @Schema(implementation = PaymentTermsDto.class))) PaymentTermsDto sourceUOMDto) throws JsonPatchException, JsonProcessingException, PaymentTermsException;



    @Operation(method = "GET", summary = "Get Payment Terms by Id", description = "getPaymentTermsById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved Payment Terms by Id",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentTermsVo.class))
            )
    })
    @Parameter(description = "Payment Terms Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<PaymentTermsVo> getPaymentTermsByCode(String code) throws PaymentTermsException;


    @Operation(method = "DELETE", summary = "Delete Payment Terms by Id", description = "deletePaymentTermsById")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment Terms edited")
    })
    @Parameter(description = "Payment Terms Identifier", name = "Id", schema = @Schema(implementation = String.class), in = ParameterIn.PATH, required = true)
    public ResponseEntity<Void> deletePaymentTermsById(String code) throws PaymentTermsException;


    @Operation(method = "GET", summary = "Search Payment Terms by query within range", description = "searchPaymentTermsByQuery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieved all Payment Terms matching given search query within range",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaymentTermsPageImplVo.class))
            )
    })
    @Parameters(value = {
            @Parameter(description = "Search value", name = "search", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Sort by", name = "sort", schema = @Schema(implementation = String.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Is ascending", name = "ascending", schema = @Schema(implementation = Boolean.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page offset", name = "offset", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true),
            @Parameter(description = "Page limit", name = "limit", schema = @Schema(implementation = Integer.class), in = ParameterIn.QUERY, allowEmptyValue = true)
    })
    public ResponseEntity<PaymentTermsPageImplVo> searchPaymentTermsByQuery(String search, String sort, Boolean ascending, Integer offset, Long limit);




}
