package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.UnitException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.UOMAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(path = UOMAPI.BASE_URI)
public class UOMController implements UOMAPI {

    private UOMService uomService;
    private ObjectMapper mapper;
    private Validator validator;

    @Autowired
    public UOMController(UOMService uomService,
                         ObjectMapper mapper,
                         Validator validator) {
        this.uomService = uomService;
        this.mapper = mapper;
        this.validator = validator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Void> postUOM(@RequestBody @Valid UOMForm form) throws UnitException {
        UOMVo uomVo = uomService.createNewUOM(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uomVo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    private UOMDto patchUOM(JsonPatch jsonPatch) throws JsonProcessingException, JsonPatchException {
        JsonNode blankUOMDtoNode = mapper.convertValue(UOMDto.builder().build(), JsonNode.class);
        JsonNode patchedUOMDtoNode = jsonPatch.apply(blankUOMDtoNode);
        UOMDto patcheUOMDto = mapper.treeToValue(patchedUOMDtoNode, new TypeReference<UOMDto>() {});
        log.debug("patchedUOMDtoNode: {}", mapper.writeValueAsString(patcheUOMDto));
        return patcheUOMDto;
    }

    @PatchMapping(path = "/{id}", consumes = HttpMediaType.APPLICATION_JSON_PATCH)
    @Override
    public ResponseEntity<Void> patchUOMByCode(@PathVariable(name = "id") String code, @RequestBody @Valid UOMDtoV2 sourceUOMDto) throws JsonPatchException, JsonProcessingException, UnitException {
        uomService.updateExistingUOMByCode(code, sourceUOMDto);
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
    public ResponseEntity<UOMVo> getUOMByCode(@PathVariable(name = "id") String code) throws UnitException {
        UOMVo uomVo = uomService.retrieveExistingUOMByCode(code);
        return ResponseEntity.ok(uomVo);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> deleteUOMById(@PathVariable(name = "id") String code) {
        uomService.deleteExistingUOMByCode(code);
        return ResponseEntity.noContent().build();
    }

    @Override
    public String getContext() {
        return "UOMController";
    }
}
