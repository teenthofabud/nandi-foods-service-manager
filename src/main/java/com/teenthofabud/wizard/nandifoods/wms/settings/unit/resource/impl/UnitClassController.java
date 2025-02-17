package com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.impl;

import com.teenthofabud.wizard.nandifoods.wms.settings.constants.HttpMediaType;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.UnitException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.resource.UnitClassAPI;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitClassService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

@Slf4j
@RestController
@RequestMapping(path = UnitClassAPI.BASE_URI)
public class UnitClassController implements UnitClassAPI {

    private UnitClassService unitClassService;
    private Validator validator;

    @Autowired
    public UnitClassController(UnitClassService unitClassService,
                               Validator validator) {
        this.unitClassService = unitClassService;
        this.validator = validator;
    }

    @GetMapping(path = "/download",
            produces = { MediaType.APPLICATION_PDF_VALUE, HttpMediaType.TEXT_CSV })
    @Override
    public StreamingResponseBody downloadUnitClass(@RequestHeader(name = HttpHeaders.ACCEPT, required = false) String accept, HttpServletResponse response) throws IOException, UnitException {
        IllegalArgumentException e = new IllegalArgumentException("Accept type header should be either " + MediaType.APPLICATION_PDF_VALUE + " or " + HttpMediaType.TEXT_CSV);
        if(!StringUtils.hasText(accept)) {
            throw e;
        }
        Optional<FileDto> optionalFileDto = Optional.empty();
        switch (accept) {
            case MediaType.APPLICATION_PDF_VALUE :
                optionalFileDto = Optional.ofNullable(unitClassService.downloadUOMAsPDF());
                break;
            case HttpMediaType.TEXT_CSV :
                optionalFileDto = Optional.ofNullable(unitClassService.downloadUOMAsCSV());
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
            log.info("Unit Class list: {} written to response", fileDto.getFileName());
        };
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public StreamingResponseBody uploadUnitClass(@RequestHeader(name = HttpHeaders.CONTENT_TYPE) String contentType,
                                           @RequestPart(value = "unitClassList", required = false) MultipartFile unitClassList) throws IOException {
        if(ObjectUtils.isEmpty(unitClassList) || unitClassList.isEmpty()) {
            throw new IllegalArgumentException("Unit Class file list is required");
        }
        if(!contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            throw new IllegalArgumentException("Content-Type header should be " + MediaType.MULTIPART_FORM_DATA_VALUE);
        }
        if(HttpMediaType.TEXT_CSV.compareTo(unitClassList.getContentType()) != 0) {
            throw new IllegalArgumentException("Document type is not " + HttpMediaType.TEXT_CSV);
        }
        FileDto fileDto = FileDto.builder()
                .fileName(unitClassList.getName())
                .rawContent(unitClassList.getInputStream())
                .build();
        log.info("Unit Class CSV list uploaded successfully");
        return null;
    }

    @Override
    public ResponseEntity<UOMPageImplVo> searchUnitClassByLongName(@RequestParam(required = false) String longName,
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
        UOMPageImplVo uomPageImplVo = unitClassService.retrieveUOMByLongName(Optional.ofNullable(longName), uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchUnitClassByLongName(@RequestParam(required = false) String longName,
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
        UOMPageImplVo uomPageImplVo = unitClassService.retrieveUOMByLongName(Optional.ofNullable(longName), uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @PostMapping(path = "/search", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<UOMPageImplVo> searchUnitClassByQueryParameter(@RequestBody(required = false) String query,
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
        Optional<@NotBlank(message = "Unit Class search query is required") String> optionalQuery = Optional.ofNullable(query);
        Set<ConstraintViolation<Optional<String>>> optionalQueryViolations = validator.validate(optionalQuery);
        if (!optionalQueryViolations.isEmpty()) {
            throw new ConstraintViolationException(optionalQueryViolations);
        }
        UOMPageImplVo uomPageImplVo = unitClassService.retrieveUOMWithinRange(optionalQuery, uomPageDto);
        return ResponseEntity.ok(uomPageImplVo);
    }

    @Override
    public String getContext() {
        return "UnitClassController";
    }
}
