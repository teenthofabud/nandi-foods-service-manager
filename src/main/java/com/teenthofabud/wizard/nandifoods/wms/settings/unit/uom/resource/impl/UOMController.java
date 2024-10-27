package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.handler.JsonFlattener;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.resource.UOMAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

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
        Set<ConstraintViolation<UOMSelfLinkageForm>> violations = form.getLinkedUOMs().stream().map(e -> validator.validate(e)).flatMap(e -> e.stream()).collect(Collectors.toSet());
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
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

    @PatchMapping(path = "/{id}", consumes = HttpMediaType.APPLICATION_JSON_PATCH)
    @Override
    public ResponseEntity<Void> patchUOMByCode(@PathVariable(name = "id") String code, @RequestBody JsonPatch jsonPatch) {
        try {
            UOMDto blankDto = UOMDto.builder().build();
            JsonNode blankDtoNode = mapper.convertValue(blankDto, JsonNode.class);
            JsonNode patchedJsonNode = jsonPatch.apply(blankDtoNode);
            UOMDto patchedDto = mapper.treeToValue(patchedJsonNode, UOMDto.class);
            log.debug("patchedDto: {}", mapper.writeValueAsString(patchedDto));
            Javers javers = JaversBuilder.javers().build();
            Diff dtoUpdates = javers.compare(blankDto, patchedDto);
            dtoUpdates.getChangesByType(PropertyChange.class).forEach(p ->
                    log.debug("{} changed from {} to {}", p.getPropertyNameWithPath(), p.getLeft(), p.getRight())
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        }
        //uomService.updateExistingUOMByCode(code, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMVo> getUOMByCode(@PathVariable(name = "id") String code) {
        UOMVo uomVo = uomService.retrieveExistingUOMByCode(code);
        return ResponseEntity.ok(uomVo);
    }

    @Override
    public String getContext() {
        return "UOMController";
    }
}
