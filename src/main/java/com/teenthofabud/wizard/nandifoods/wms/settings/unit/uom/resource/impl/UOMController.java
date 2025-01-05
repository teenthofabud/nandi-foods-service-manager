package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.handler.JsonFlattener;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

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
        JsonNode blankUOMDtoNode = mapper.convertValue(UOMDto.builder().build(), JsonNode.class);
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
    public StreamingResponseBody downloadUOM(@RequestHeader(name = HttpHeaders.ACCEPT, required = false) String accept, HttpServletResponse response) throws IOException {
        IllegalArgumentException e = new IllegalArgumentException("Accept type header should be either " + MediaType.APPLICATION_PDF_VALUE + " or " + HttpMediaType.TEXT_CSV);
        if(!StringUtils.hasText(accept)) {
            throw e;
        }
        Optional<FileDto> optionalFileDto = Optional.empty();
        switch (accept) {
            case MediaType.APPLICATION_PDF_VALUE :
                optionalFileDto = Optional.ofNullable(uomService.downloadUOMAsPDF());
                break;
            case HttpMediaType.TEXT_CSV :
                optionalFileDto = Optional.ofNullable(uomService.downloadUOMAsCSV());
                break;
            default: throw e;
        }
        FileDto fileDto = optionalFileDto.get();
        response.setContentType(accept);
        response.setHeader(
                HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileDto.getFileName() + "\"");
        InputStream finalIs = fileDto.getRawContent();
        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = finalIs.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            log.info("UOM document: {} written to response", fileDto.getFileName());
        };
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public StreamingResponseBody uploadUOM(@RequestHeader(name = HttpHeaders.CONTENT_TYPE) String contentType,
                                           @RequestPart(value = "uomFile", required = false) MultipartFile uomFile) throws IOException {
        if(ObjectUtils.isEmpty(uomFile) || uomFile.isEmpty()) {
            throw new IllegalArgumentException("UOM file is required");
        }
        if(!contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            throw new IllegalArgumentException("Content-Type header should be " + MediaType.MULTIPART_FORM_DATA_VALUE);
        }
        if(HttpMediaType.TEXT_CSV.compareTo(uomFile.getContentType()) != 0) {
            throw new IllegalArgumentException("Document type is not " + HttpMediaType.TEXT_CSV);
        }
        FileDto fileDto = FileDto.builder()
                .fileName(uomFile.getName())
                .rawContent(uomFile.getInputStream())
                .build();
        log.info("UOM CSV list uploaded successfully");
        return null;
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> deleteUOMById(@PathVariable(name = "id") String code) {
        uomService.deleteExistingUOMByCode(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UOMPageImplVo> searchUOMByLongNameWithinRange(@RequestParam(required = false) String longName,
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
        UOMPageImplVo uomPageImplVo = uomService.retrieveUOMByLongName(Optional.ofNullable(longName), uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchUOMByLongNameWithinRange(@RequestParam(required = false) String longName,
                                                                        @RequestParam(required = false) String sort,
                                                                        @RequestParam(required = false) String status,
                                                                        @RequestParam(required = false) Boolean ascending,
                                                                        @RequestParam(required = false) Integer offset,
                                                                        @RequestParam(required = false) Long limit) {
        UOMPageDto uomPageDto = UOMPageDto.builder()
                .offset(Optional.ofNullable(offset))
                .limit(Optional.ofNullable(limit))
                .sort(Optional.ofNullable(sort))
                .status(Optional.ofNullable(status))
                .ascending(Optional.ofNullable(ascending))
                .build();
        Set<ConstraintViolation<UOMPageDto>> violations = validator.validate(uomPageDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        UOMPageImplVo uomPageImplVo = uomService.retrieveUOMByLongName(Optional.ofNullable(longName), uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @PostMapping(path = "/search", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchUOMByQueryParameterWithinRange(@RequestBody(required = false) String query,
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
        UOMPageImplVo uomPageImplVo = uomService.retrieveUOMWithinRange(optionalQuery, uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @Override
    public String getContext() {
        return "UOMController";
    }
}
