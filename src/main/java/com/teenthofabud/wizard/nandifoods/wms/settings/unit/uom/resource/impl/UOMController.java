package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.handler.JsonFlattener;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.UOMAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = UOMAPI.BASE_URI)
public class UOMController implements UOMAPI {

    private UOMService uomService;
    private ObjectMapper mapper;
    private JsonFlattener jsonFlattener;
    private Validator validator;

    @Autowired
    public UOMController(UOMService uomService,
                         ObjectMapper mapper,
                         JsonFlattener jsonFlattener,
                         Validator validator) {
        this.uomService = uomService;
        this.mapper = mapper;
        this.jsonFlattener = jsonFlattener;
        this.validator = validator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> postUOM(@RequestBody @Valid UOMForm form) {
        // only applicable to approve process
        /*Set<ConstraintViolation<UnitClassSelfLinkageForm>> violations = form.getLinkedUOMs().stream().map(e -> validator.validate(e)).flatMap(e -> e.stream()).collect(Collectors.toSet());
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }*/
        UOMVo uomVo = uomService.createNewUOM(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uomVo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public static Object parseJsonNode(JsonNode v) {
        Object o = null;
        switch (v.getNodeType()) {
            case STRING -> o = v.textValue();
            case NUMBER -> o = v.doubleValue();
            case BOOLEAN -> o = v.booleanValue();
        }
        return o;
    }

    private UOMDto patchUOM(JsonPatch jsonPatch) throws JsonProcessingException, JsonPatchException {
        UOMDto blankUOMDto = new UOMDto();
        JsonNode blankUOMDtoNode = mapper.convertValue(blankUOMDto, JsonNode.class);
        JsonNode patchedUOMDtoNode = jsonPatch.apply(blankUOMDtoNode);
        UOMDto patcheUOMDto = mapper.treeToValue(patchedUOMDtoNode, UOMDto.class);
        log.debug("patchedUOMDtoNode: {}", mapper.writeValueAsString(patcheUOMDto));
        return patcheUOMDto;
    }

    @PatchMapping(path = "/{id}", consumes = HttpMediaType.APPLICATION_JSON_PATCH)
    @Override
    public ResponseEntity<Void> patchUOMByCode(@PathVariable(name = "id") String code, @RequestBody(required = false) JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        if(ObjectUtils.isEmpty(jsonPatch)) {
            throw new IllegalArgumentException("Json patch with changes is required");
        }
        UOMDto patcheUOMDto = patchUOM(jsonPatch);
        Set<ConstraintViolation<UOMDto>> violations = validator.validate(patcheUOMDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        uomService.updateExistingUOMByCode(code, patcheUOMDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}/approve", consumes = HttpMediaType.APPLICATION_JSON_PATCH)
    @Override
    public ResponseEntity<Void> approveSavedUOMById(@PathVariable(name = "id") String code, @RequestBody(required = false) JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
        Optional<UOMDto> optionallyPatchedUOMDto = ObjectUtils.isEmpty(jsonPatch) ? Optional.empty() : Optional.of(patchUOM(jsonPatch));
        Set<ConstraintViolation<Optional<UOMDto>>> violations = validator.validate(optionallyPatchedUOMDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        uomService.approveSavedUOMByCode(code, optionallyPatchedUOMDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMVo> getUOMByCode(@PathVariable(name = "id") String code) {
        UOMVo uomVo = uomService.retrieveExistingUOMByCode(code);
        return ResponseEntity.ok(uomVo);
    }

    @GetMapping(path = "/download",
            produces = { MediaType.APPLICATION_PDF_VALUE, HttpMediaType.TEXT_CSV })
    @Override
    public StreamingResponseBody downloadAllUOM(@RequestHeader(name = HttpHeaders.ACCEPT, required = false) String accept, HttpServletResponse response) {
        IllegalArgumentException e = new IllegalArgumentException("Accept type header should be either " + MediaType.APPLICATION_PDF_VALUE + " or " + HttpMediaType.TEXT_CSV);
        if(!StringUtils.hasText(accept)) {
            throw e;
        }
        switch (accept) {
            case MediaType.APPLICATION_PDF_VALUE : return null;
            case HttpMediaType.TEXT_CSV : return null;
            default: throw e;
        }
        /*response.setContentType(fileInfo.getContentType());
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileInfo.getFilename() + "\"");

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            InputStream inputStream = fileInfo.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };*/
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> deleteUOMById(@PathVariable(name = "id") String code) {
        uomService.deleteExistingUOMByCode(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchAllUOMByLongNameWithinRange(@RequestParam(required = false) String longName,
                                                                        @RequestParam(required = false) String sort,
                                                                        @RequestParam(required = false) Boolean ascending,
                                                                        @RequestParam(required = false) Integer offset,
                                                                        @RequestParam(required = false) Long limit) {
        UOMPageDto uomPageDto = UOMPageDto.builder()
                .offset(Optional.ofNullable(offset))
                .limit(Optional.ofNullable(limit))
                .sort(Optional.ofNullable(sort))
                .ascending(Optional.ofNullable(ascending))
                .build();
        Set<ConstraintViolation<UOMPageDto>> violations = validator.validate(uomPageDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        UOMPageImplVo uomPageImplVo = uomService.retrieveAllUOMByLongName(Optional.ofNullable(longName), uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @PostMapping(path = "/search", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchAllUOMByQueryParameterWithinRange(@RequestBody(required = false) String query,
                                                                                 @RequestParam(required = false) String sort,
                                                                                 @RequestParam(required = false) Boolean ascending,
                                                                                 @RequestParam(required = false) Integer offset,
                                                                                 @RequestParam(required = false) Long limit) {
        UOMPageDto uomPageDto = UOMPageDto.builder()
                .offset(Optional.ofNullable(offset))
                .limit(Optional.ofNullable(limit))
                .sort(Optional.ofNullable(sort))
                .ascending(Optional.ofNullable(ascending))
                .build();
        Set<ConstraintViolation<UOMPageDto>> uomPageDtoViolations = validator.validate(uomPageDto);
        if (!uomPageDtoViolations.isEmpty()) {
            throw new ConstraintViolationException(uomPageDtoViolations);
        }
        Optional<@NotBlank(message = "UOM search query is required") String> optionalQuery = Optional.ofNullable(query);
        Set<ConstraintViolation<Optional<String>>> optionalQueryViolations = validator.validate(optionalQuery);
        if (!optionalQueryViolations.isEmpty()) {
            throw new ConstraintViolationException(optionalQueryViolations);
        }
        UOMPageImplVo uomPageImplVo = uomService.retrieveAllUOMWithinRange(optionalQuery, uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @Override
    public String getContext() {
        return "UOMController";
    }
}
