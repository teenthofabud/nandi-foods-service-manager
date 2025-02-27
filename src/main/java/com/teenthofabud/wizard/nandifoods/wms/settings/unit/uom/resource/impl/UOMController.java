package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
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
    public ResponseEntity<Void> postUOM(@RequestBody @Valid UOMForm form) {
        UOMVo uomVo = uomService.createNewUOM(form);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(uomVo.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    private UOMDtoV2 mergePatchUOM(JsonMergePatch jsonMergePatch) throws JsonPatchException, JsonProcessingException {
        JsonNode blankUOMDtoNode = mapper.convertValue(UOMDtoV2.builder().build(), JsonNode.class);
        JsonNode patchedUOMDtoNode = jsonMergePatch.apply(blankUOMDtoNode);
        UOMDtoV2 patcheUOMDto = mapper.treeToValue(patchedUOMDtoNode, new TypeReference<UOMDtoV2>() {});
        return patcheUOMDto;
    }

    @PatchMapping(path = "/{id}", consumes = HttpMediaType.APPLICATION_JSON_MERGE_PATCH)
    @Override
    public ResponseEntity<Void> patchUOMByCode(@PathVariable(name = "id") String code, @RequestBody JsonMergePatch jsonMergePatch) throws JsonPatchException, JsonProcessingException {
        Optional<UOMDtoV2> optionallyPatchedUOMDto = ObjectUtils.isEmpty(jsonMergePatch) ? Optional.empty() : Optional.of(mergePatchUOM(jsonMergePatch));
        Set<ConstraintViolation<Optional<UOMDtoV2>>> violations = validator.validate(optionallyPatchedUOMDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if(optionallyPatchedUOMDto.isPresent())
        uomService.updateExistingUOMByCode(code, optionallyPatchedUOMDto.get());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{id}/approve", consumes = HttpMediaType.APPLICATION_JSON_MERGE_PATCH)
    @Override
    public ResponseEntity<Void> approveSavedUOMById(@PathVariable(name = "id") String code, @RequestBody(required = false) JsonMergePatch jsonMergePatch) throws JsonPatchException, JsonProcessingException {
        Optional<UOMDtoV2> optionallyPatchedUOMDto = ObjectUtils.isEmpty(jsonMergePatch) ? Optional.empty() : Optional.of(mergePatchUOM(jsonMergePatch));
        Set<ConstraintViolation<Optional<UOMDtoV2>>> violations = validator.validate(optionallyPatchedUOMDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if(optionallyPatchedUOMDto.isPresent())
        uomService.approveSavedUOMByCode(code, optionallyPatchedUOMDto.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMVo> getUOMByCode(@PathVariable(name = "id") String code) {
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
