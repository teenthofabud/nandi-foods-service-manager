package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.impl;

import com.diffplug.common.base.Errors;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.teenthofabud.wizard.nandifoods.wms.handler.ColumnPositionNameMappingStrategy;
import com.teenthofabud.wizard.nandifoods.wms.handler.ComparativeUpdateHandler;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassCrossLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.HUEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.entity.UOMHULinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.repository.HURepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.hu.repository.UOMHULinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.PUEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.entity.UOMPULinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.repository.PURepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.pu.repository.UOMPULinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer.UnitClassCrossLinkageToUOMHULinkageEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer.UnitClassCrossLinkageToUOMPULinkageEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer.UnitClassSelfLinkageToUOMSelfLinkageEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMCrossLinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMMeasuredValuesJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMSelfLinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassSelfLinkageVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UOMServiceImpl implements UOMService, ComparativeUpdateHandler<UOMEntity> {

    private UOMJpaRepository uomJpaRepository;
    private UOMFormToEntityConverter uomFormToEntityConverter;
    private UOMEntityToVoConverter uomEntityToVoConverter;
    private UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter unitClassToUOMMeasuredValuesConverter;
    private UOMMeasuredValuesJpaRepository uomMeasuredValuesJpaRepository;
    private UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomToUnitClassMeasuredValuesConverter;
    private UnitClassSelfLinkageToUOMSelfLinkageEntityReducer unitClassSelfLinkageToUOMSelfLinkageEntityReducer;
    private UOMSelfLinkageJpaRepository uomSelfLinkageJpaRepository;
    private UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter;
    private UOMPageDtoToPageableConverter uomPageDtoToPageableConverter;
    private BeanUtilsBean beanUtilsBean;
    private UnitClassCrossLinkageToUOMPULinkageEntityReducer unitClassCrossLinkageToUOMPULinkageEntityReducer;
    private UnitClassCrossLinkageToUOMHULinkageEntityReducer unitClassCrossLinkageToUOMHULinkageEntityReducer;
    private UOMCrossLinkageJpaRepository uomCrossLinkageJpaRepository;
    private HURepository huRepository;
    private PURepository puRepository;
    private UOMPULinkageJpaRepository uomPULinkageJpaRepository;
    private UOMHULinkageJpaRepository uomHULinkageJpaRepository;
    private Javers javers;
    private UOMSummaryProjectionToVoConverter uomSummaryProjectionToVoConverter;
    private UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter;
    private UOMEntityToDtoV2Converter uomEntityToDtoV2Converter;
    //private UOMSummaryProjectionRepository uomSummaryProjectionRepository;

    private List<String> searchFields;
    private String fileNameDateFormat;
    private String csvFileNameFormat;
    private String pdfFileNameFormat;

    @Autowired
    public UOMServiceImpl(UOMJpaRepository uomJpaRepository,
                          UOMFormToEntityConverter uomFormToEntityConverter,
                          UOMEntityToVoConverter uomEntityToVoConverter,
                          UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter unitClassToUOMMeasuredValuesConverter,
                          UOMMeasuredValuesJpaRepository uomMeasuredValuesJpaRepository,
                          UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomToUnitClassMeasuredValuesConverter,
                          UnitClassSelfLinkageToUOMSelfLinkageEntityReducer unitClassSelfLinkageToUOMSelfLinkageEntityReducer,
                          UOMSelfLinkageJpaRepository uomSelfLinkageJpaRepository,
                          UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter,
                          UOMPageDtoToPageableConverter uomPageDtoToPageableConverter,
                          BeanUtilsBean beanUtilsBean,
                          UnitClassCrossLinkageToUOMPULinkageEntityReducer unitClassCrossLinkageToUOMPULinkageEntityReducer,
                          UOMCrossLinkageJpaRepository uomCrossLinkageJpaRepository,
                          HURepository huRepository,
                          PURepository puRepository,
                          UnitClassCrossLinkageToUOMHULinkageEntityReducer unitClassCrossLinkageToUOMHULinkageEntityReducer,
                          UOMPULinkageJpaRepository uomPULinkageJpaRepository,
                          UOMHULinkageJpaRepository uomHULinkageJpaRepository,
                          Javers javers,
                          UOMSummaryProjectionToVoConverter uomSummaryProjectionToVoConverter,
                          UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter,
                          UOMEntityToDtoV2Converter uomEntityToDtoV2Converter,
                          //UOMSummaryProjectionRepository uomSummaryProjectionRepository,
                          @Value("#{'${wms.settings.uom.search.fields}'.split(',')}") List<String> searchFields,
                          @Value("${wms.settings.unit.fileNameDateTimeFormat}") String fileNameDateFormat,
                          @Value("${wms.settings.uom.fileNameFormat.csv}") String csvFileNameFormat,
                          @Value("${wms.settings.uom.fileNameFormat.pdf}") String pdfFileNameFormat) {
        this.uomJpaRepository = uomJpaRepository;
        this.uomFormToEntityConverter = uomFormToEntityConverter;
        this.uomEntityToVoConverter = uomEntityToVoConverter;
        this.unitClassToUOMMeasuredValuesConverter = unitClassToUOMMeasuredValuesConverter;
        this.uomMeasuredValuesJpaRepository = uomMeasuredValuesJpaRepository;
        this.uomToUnitClassMeasuredValuesConverter = uomToUnitClassMeasuredValuesConverter;
        this.unitClassSelfLinkageToUOMSelfLinkageEntityReducer = unitClassSelfLinkageToUOMSelfLinkageEntityReducer;
        this.uomSelfLinkageJpaRepository = uomSelfLinkageJpaRepository;
        this.uomSelfLinkageEntityToVoConverter = uomSelfLinkageEntityToVoConverter;
        this.uomPageDtoToPageableConverter = uomPageDtoToPageableConverter;
        this.beanUtilsBean = beanUtilsBean;
        this.unitClassCrossLinkageToUOMPULinkageEntityReducer = unitClassCrossLinkageToUOMPULinkageEntityReducer;
        this.uomCrossLinkageJpaRepository = uomCrossLinkageJpaRepository;
        this.huRepository = huRepository;
        this.puRepository = puRepository;
        this.unitClassCrossLinkageToUOMHULinkageEntityReducer = unitClassCrossLinkageToUOMHULinkageEntityReducer;
        this.uomPULinkageJpaRepository = uomPULinkageJpaRepository;
        this.uomHULinkageJpaRepository = uomHULinkageJpaRepository;
        this.javers = javers;
        this.uomSummaryProjectionToVoConverter = uomSummaryProjectionToVoConverter;
        this.uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter = uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter;
        this.uomEntityToDtoV2Converter = uomEntityToDtoV2Converter;
        this.searchFields = searchFields;
        this.fileNameDateFormat = fileNameDateFormat;
        this.csvFileNameFormat = csvFileNameFormat;
        this.pdfFileNameFormat = pdfFileNameFormat;
        //this.uomSummaryProjectionRepository = uomSummaryProjectionRepository;
    }

    private void createNewUOMMeasuredValues(UOMEntity uomEntity, UnitClassMeasuredValuesForm form) {
        UOMMeasuredValuesEntity uomMeasuredValuesEntity = unitClassToUOMMeasuredValuesConverter.convert(form);
        uomMeasuredValuesEntity.setUom(uomEntity);// because of bidirectional one to many strategy by vlad mihalcea the entire JPA relationship needs to be managed by hand
        uomEntity.addMeasuredValue(uomMeasuredValuesEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), form.getMeasurementSystem(), uomMeasuredValuesEntity.getId());
    }

    private void selfLink(UOMEntity from, Optional<List<UnitClassSelfLinkageForm>> optionalUnitClassSelfLinkageForms) {
        if(optionalUnitClassSelfLinkageForms.isEmpty()) {
            log.debug("No UOMs linked! Skipping UOM self linkage logic");
            return;
        }
        for(UnitClassSelfLinkageForm e : optionalUnitClassSelfLinkageForms.get()) {
            Optional<UOMEntity> optionalTo = uomJpaRepository.findByCode(e.getCode());
            if(optionalTo.isEmpty()) {
                throw new IllegalArgumentException("UOM does not exist with code: " + e.getCode());
            }
            UOMEntity to = optionalTo.get();
            UOMSelfLinkageEntity uomSelfLinkageEntity = unitClassSelfLinkageToUOMSelfLinkageEntityReducer.reduce(e, from, to);
            from.addConversionFromUOM(uomSelfLinkageEntity);
            log.debug("UOM with code: {} linked to UOM with code: {}", from.getCode(), uomSelfLinkageEntity.getToUom().getCode());
        }
    }

    private void puLink(UOMEntity from, UnitClassCrossLinkageForm e) {
        Optional<PUEntity> optionalTo = puRepository.findByCode(e.getCode());
        if(optionalTo.isEmpty()) {
            throw new IllegalArgumentException("PU does not exist with code: " + e.getCode());
        }
        PUEntity to = optionalTo.get();
        UOMPULinkageEntity uomPULinkageEntity = unitClassCrossLinkageToUOMPULinkageEntityReducer.reduce(e, from, to);
        from.addUOMPULinkage(uomPULinkageEntity);
        log.debug("UOM with code: {} linked to PU with code: {}", from.getCode(), to.getCode());
    }

    private void huLink(UOMEntity from, UnitClassCrossLinkageForm e) {
        Optional<HUEntity> optionalTo = huRepository.findByCode(e.getCode());
        if(optionalTo.isEmpty()) {
            throw new IllegalArgumentException("HU does not exist with code: " + e.getCode());
        }
        HUEntity to = optionalTo.get();
        UOMHULinkageEntity uomHULinkageEntity = unitClassCrossLinkageToUOMHULinkageEntityReducer.reduce(e, from, to);
        //uomHULinkageEntity = uomHULinkageJpaRepository.save(uomHULinkageEntity);
        from.addUOMHULinkage(uomHULinkageEntity);
        //from = uomJpaRepository.save(from);
        log.debug("UOM with code: {} linked to HU with code: {}", from.getCode(), to.getCode());
    }

    private void crossLink(UOMEntity from, Optional<List<UnitClassCrossLinkageForm>> optionalUnitClassCrossLinkageForms) {
        if(optionalUnitClassCrossLinkageForms.isEmpty()) {
            log.debug("No PU/HU linked! Skipping UOM cross linkage logic");
            return;
        }
        List<UnitClassCrossLinkageForm> linkedPUHUs = optionalUnitClassCrossLinkageForms.get();
        linkedPUHUs.stream().filter(e -> e.getType().compareTo(UnitClass.PU) == 0).forEach(e -> puLink(from, e));
        linkedPUHUs.stream().filter(e -> e.getType().compareTo(UnitClass.HU) == 0).forEach(e -> huLink(from, e));
    }

    private UnitClassMeasuredValuesVo getUnitClassMeasuredValuesFor(UOMEntity uomEntity, MeasurementSystem measurementSystem) {
        UnitClassMeasuredValuesVo unitClassMeasuredValuesVo = uomEntity.getMeasuredValues()
                .stream()
                .filter(umv -> umv.getMeasurementSystem().compareTo(measurementSystem) == 0)
                .map(f -> uomToUnitClassMeasuredValuesConverter.convert(f))
                .findFirst()
                .get();
        return unitClassMeasuredValuesVo;
    }

    private void validateMutualRelationsBetweenCollectionItems(UOMForm form) {
        if(form.getLinkedUOMs().isPresent()) {
            List<UnitClassSelfLinkageForm> selfLinksList = form.getLinkedUOMs().get();
            Set<UnitClassSelfLinkageForm> selfLinksSet = selfLinksList.stream().collect(Collectors.toSet());
            if(selfLinksList.size() != selfLinksSet.size()) {
                log.debug("Same UOM is being tried to linked multiple times");
                throw new IllegalStateException("Duplicate UOM self links");
            }
        }
        validateMutualRelationsBetweenMeasuredValues(form, MeasurementSystem.IMPERIAL);
        validateMutualRelationsBetweenMeasuredValues(form, MeasurementSystem.SI);
    }

    private void validateMutualRelationsBetweenMeasuredValues(UOMForm form, MeasurementSystem measurementSystem) {
       Long countMeasuredValues = form.getMeasuredValues().stream().filter(f -> f.getMeasurementSystem().compareTo(measurementSystem) == 0).count();
        if(countMeasuredValues != 1l) {
            log.debug("Invalid number : {} of measured values for {} metric system", countMeasuredValues, measurementSystem);
            throw new IllegalArgumentException("Invalid count of " + measurementSystem.name() + " measured values");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UOMVo createNewUOM(UOMForm form) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(form.getCode());
        if(optionalUOMEntity.isPresent()) {
            throw new IllegalStateException("UOM already exists with id: " + form.getCode());
        }

        validateMutualRelationsBetweenCollectionItems(form);

        // Save UOM
        final UOMEntity uomEntity = uomFormToEntityConverter.convert(form);

        // Save measured values for all metric systems
        form.getMeasuredValues().stream().forEach(f -> createNewUOMMeasuredValues(uomEntity, f));

        uomJpaRepository.save(uomEntity);

        // Save linked UOMs
        selfLink(uomEntity, form.getLinkedUOMs());

        // Save linked PU/HU
        crossLink(uomEntity, form.getLinkedPUHUs());

        uomJpaRepository.save(uomEntity);
        log.info("UOM saved with id: {}", uomEntity.getId());

        // Prepare UOM for display
        UOMVo uomVo = uomEntityToVoConverter.convert(uomEntity);
        return uomVo;
    }

    @Transactional
    @Override
    public UOMVo retrieveExistingUOMByCode(String code) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        UOMEntity uomEntity = optionalUOMEntity.get();
        log.debug("UOM found with code: {}", uomEntity.getCode());
        UOMVo uomVo = uomEntityToVoConverter.convert(uomEntity);
        List<UnitClassSelfLinkageVo> fromVos = uomEntity.getFromUOMs().stream()
                .map(e -> uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter.convert(e))
                .collect(Collectors.toList());
        log.debug("Retrieved all linked UOMs to this UOM with code: {}", uomEntity.getCode());
        uomVo.setSelfLinksTo(fromVos);
        log.info("Details available for UOM with code: {}", uomEntity.getCode());
        return uomVo;
    }

    private void removeMeasuredValues(UOMEntity from) {
        List<UOMMeasuredValuesEntity> measuredValuesEntityList = new CopyOnWriteArrayList<>(from.getMeasuredValues());
        measuredValuesEntityList.stream().forEach(f -> {
            f.setUom(null);
            from.removeUOMMeasuredValue(f);
        });
        uomMeasuredValuesJpaRepository.deleteByUomId(from.getId());
        log.debug("Removed all measured values for UOM with code: {}", from.getCode());
    }

    private void unlinkSelf(UOMEntity from) {
        List<UOMSelfLinkageEntity> fromUOMs = new CopyOnWriteArrayList<>(from.getFromUOMs());
        fromUOMs.stream().forEach(f -> {
            f.setFromUom(null);
            f.setToUom(null);
            from.removeConversionFromUOM(f);
        });
        uomSelfLinkageJpaRepository.deleteByFromUomId(from.getId());
        log.debug("Removed all UOM links that UOM with code: {} is related to", from.getCode());
        uomSelfLinkageJpaRepository.deleteByToUomId(from.getId());
        log.debug("Removed all UOM links that UOM with code: {} is related with", from.getCode());
        log.info("Removed all UOM self links from UOM with code: {}", from.getCode());
    }

    private void unlinkCross(UOMEntity from) {
        for(UOMPULinkageEntity e : from.getPuLinks()) {
            from.removeUOMPULinkage(e);
            log.debug("UOM with code: {} unlinked from PU with code: {}", from.getCode(), e.getPu().getCode());
            uomPULinkageJpaRepository.delete(e);
        }
        for(UOMHULinkageEntity e : from.getHuLinks()) {
            from.removeUOMHULinkage(e);
            log.debug("UOM with code: {} unlinked from HU with code: {}", from.getCode(), e.getHu().getCode());
            uomHULinkageJpaRepository.delete(e);
        }
        uomJpaRepository.save(from);
        log.info("Removed all PU and HU links from UOM with code: {}", from.getCode());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteExistingUOMByCode(String code) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        UOMEntity uomEntity = optionalUOMEntity.get();
        log.debug("UOM found with code: {}", uomEntity.getCode());
        // remove measured values
        removeMeasuredValues(uomEntity);
        // unlink measured values, self, pu hu
        unlinkSelf(uomEntity);
        unlinkCross(uomEntity);
        uomJpaRepository.deleteAllInBatch(List.of(uomEntity));
        log.debug("UOM deleted with code: {}", uomEntity.getCode());
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto) {
        Pageable pageable = uomPageDtoToPageableConverter.convert(uomPageDto);
        Page<UOMEntity> uomEntityPage = new PageImpl<>(List.of());
        Specification<UOMEntity> uomSearchQueryLikeLongNameSpecification = optionalLongName.map(o -> uomJpaRepository.likeProperty("longName", optionalLongName.get()))
                .orElse(uomJpaRepository.skipThisSpecificationWithEmptyCriteriaConjunction());
        Specification<UOMEntity> uomSearchQueryEqualStatusSpecification = uomPageDto.getStatus().map(o ->
                uomJpaRepository.equalsProperty("status", UnitClassStatus.valueOf(uomPageDto.getStatus().get()).getOrdinal()))
                .orElse(uomJpaRepository.skipThisSpecificationWithEmptyCriteriaConjunction());
        Specification<UOMEntity> uomSearchQuerySpecification = Specification
                .where(uomSearchQueryLikeLongNameSpecification).and(uomSearchQueryEqualStatusSpecification);
        log.debug("Created specification for UOMEntity using long name: {} and status: {} and page", optionalLongName, uomPageDto.getStatus(), pageable);
        uomEntityPage = uomJpaRepository.findAll(uomSearchQuerySpecification, pageable);
        List<UOMVo> uomVoList = uomEntityPage.stream().map(i -> {
            List<UnitClassSelfLinkageVo> unitClassSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(unitClassSelfLinkageVoList);
            return uomVo;
        }).collect(Collectors.toList());
        UOMPageImplVo uomPageImplVo = new UOMPageImplVo(uomVoList, uomEntityPage.getPageable(), uomEntityPage.getTotalElements());
        log.debug("Found {} UOM in page {}", uomEntityPage.getTotalElements(), uomPageImplVo.getNumber());
        return uomPageImplVo;
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveUOMWithinRange(Optional<String> optionalQuery, @Valid UOMPageDto uomPageDto) {
        Pageable pageable = uomPageDtoToPageableConverter.convert(uomPageDto);
        Page<UOMEntity> uomEntityPage = new PageImpl<>(List.of());
        if(optionalQuery.isPresent()) {
            String q = optionalQuery.get();
            List<Specification<UOMEntity>> uomEntitySpecifications = searchFields.stream()
                    .flatMap(k -> Arrays.stream(q.split("\\s+"))
                            .map(v -> uomJpaRepository.likeProperty(k, v))).collect(Collectors.toList());
            Specification<UOMEntity> uomSearchQuerySpecification = Specification.anyOf(uomEntitySpecifications);
            log.debug("Created specification for UOMEntity using query: {}", q);
            uomEntityPage = uomJpaRepository.findAll(uomSearchQuerySpecification, pageable);
        } else {
            log.debug("Searching UOMEntity with page: {}", pageable);
            uomEntityPage = uomJpaRepository.findAll(pageable);
        }
        List<UOMVo> uomVoList = uomEntityPage.stream().map(i -> {
            List<UnitClassSelfLinkageVo> unitClassSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(unitClassSelfLinkageVoList);
            return uomVo;
        }).collect(Collectors.toList());
        UOMPageImplVo uomPageImplVo = new UOMPageImplVo(uomVoList, uomEntityPage.getPageable(), uomEntityPage.getTotalElements());
        log.debug("Found {} UOM in page {}", uomEntityPage.getTotalElements(), uomPageImplVo.getNumber());
        return uomPageImplVo;
    }

    private void uomUpdateHelper(UOMEntity uomEntity, Optional<UOMDto> optionallyPatchedUOMDto) {
        if(optionallyPatchedUOMDto.isPresent()) {
            UOMDto patchedUOMDto = optionallyPatchedUOMDto.get();
            UOMDto blankUOMDto = UOMDto.builder().build();
            Diff rawDtoUpdates = javers.compare(blankUOMDto, patchedUOMDto);
            log.debug("UOM found with code: {}", uomEntity.getCode());
            log.debug("Mapping updates from patched UOMDto to UOMEntity");
            rawDtoUpdates.getChangesByType(PropertyChange.class).forEach(Errors.rethrow().wrap(p -> {
                log.debug("{} changed from {} to {}", p.getPropertyNameWithPath(), p.getLeft(), p.getRight());
                beanUtilsBean.copyProperty(uomEntity, p.getPropertyNameWithPath(), p.getRight());
            }));
        }
    }

    @Transactional
    @Override
    public void updateExistingUOMByCode(String code, UOMDto patchedUOMDto) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        log.debug("UOM does exists with code: {}", code);
        UOMEntity uomEntity = optionalUOMEntity.get();
        uomUpdateHelper(uomEntity, Optional.of(patchedUOMDto));
        uomJpaRepository.save(uomEntity);
        log.info("Updated UOMEntity with id: {}", uomEntity.getId());
    }

    @Transactional
    @Override
    public void updateExistingUOMByCode(String code, UOMDtoV2 sourceUOMDto) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        log.debug("UOM does exists with code: {}", code);
        UOMEntity uomEntity = optionalUOMEntity.get();
        UOMDtoV2 targetUOMDto = uomEntityToDtoV2Converter.convert(uomEntity);
        Diff dtoChanges = javers.compare(targetUOMDto, sourceUOMDto);
        uomEntity = comparativeUpdate(dtoChanges, uomEntity);
        uomJpaRepository.save(uomEntity);
        log.info("Updated UOMEntity with id: {}", uomEntity.getId());
    }

    @Transactional
    @Override
    public void approveSavedUOMByCode(String code, Optional<UOMDto> optionallyPatchedUOMDto) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        UOMEntity uomEntity = optionalUOMEntity.get();
        if(uomEntity.getStatus().compareTo(UnitClassStatus.ACTIVE) == 0) {
            throw new IllegalStateException("UOM already approved with id: " + code);
        }
        uomUpdateHelper(uomEntity, optionallyPatchedUOMDto);
        LocalDateTime approvalTime = LocalDateTime.now();
        uomEntity.setStatus(UnitClassStatus.ACTIVE);
        log.debug("UOMEntity with id: {} will be activated", uomEntity.getId());
        uomJpaRepository.save(uomEntity);
        log.info("Approved UOMEntity with id: {}", uomEntity.getId());
    }

    @Transactional
    @Override
    public FileDto downloadUOMAsCSV() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        ColumnPositionNameMappingStrategy<UOMVo> mappingStrategy = new ColumnPositionNameMappingStrategy<UOMVo>(UOMVo.class);
        mappingStrategy.setType(UOMVo.class);
        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        //List<UOMSummaryProjection> uomSummaryList = uomSummaryProjectionRepository.findAllWithMeasuredValueForMetricSystem(MeasurementSystem.SI);
        //List<UOMSummaryVo> uomSummaryVoList = uomSummaryList.stream().map(f -> uomSummaryProjectionToVoConverter.convert(f)).collect(Collectors.toList());
        StatefulBeanToCsv<UOMVo> sbc = new StatefulBeanToCsvBuilder<UOMVo>(writer)
        //StatefulBeanToCsv<UOMSummaryVo> sbc = new StatefulBeanToCsvBuilder<UOMSummaryVo>(writer)
                .withQuotechar('\'')
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withMappingStrategy(mappingStrategy)
                .build();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fileNameDateFormat);
        String csvFileName = String.format(csvFileNameFormat, dtf.format(LocalDateTime.now()));
        try {
            log.debug("Creating UOM list in CSV with {} UOMs", uomVoList.size());
            sbc.write(uomVoList);
            //sbc.write(uomSummaryVoList);
            streamWriter.flush();
        } catch (CsvDataTypeMismatchException e) {
            throw new IllegalArgumentException("CSV bean field not configured properly", e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new IllegalArgumentException("CSV bean field is empty", e);
        }
        FileDto fileDto = FileDto.builder()
                .fileName(csvFileName)
                .rawContent(new ByteArrayInputStream(stream.toByteArray()))
                .build();
        log.info("UOM is available for download in CSV as {}", fileDto.getFileName());
        return fileDto;
    }

    @Override
    public FileDto downloadUOMAsPDF() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(stream));
        Document doc = new Document(pdfDocument);

        List<Field> fields = Stream.concat(
                Arrays.stream(UOMVo.class.getDeclaredFields()), Arrays.stream(UOMVo.class.getSuperclass().getDeclaredFields()))
                .filter(f -> f.isAnnotationPresent(CsvBindByName.class) && f.isAnnotationPresent(CsvBindByPosition.class))
                .collect(Collectors.toList());
        fields.sort(Comparator.comparing(o -> o.getDeclaredAnnotation(CsvBindByPosition.class).position()));
        Table table = new Table(fields.size()).useAllAvailableWidth();
        fields.stream().forEach(f -> table.addHeaderCell(
                new Cell().add(
                        new Paragraph(
                                f.getDeclaredAnnotation(CsvBindByName.class).column()))
                        .setBackgroundColor(DeviceGray.GRAY))
                .setTextAlignment(TextAlignment.LEFT));

        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        log.debug("Creating UOM list in PDF with {} UOMs", uomVoList.size());
        uomVoList.stream().forEach(f ->
                fields.stream().forEach(d -> {
                try {
                    table.addCell(new Cell().add(new Paragraph(BeanUtils.getProperty(f, d.getName()))));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }));

        doc.add(table);
        doc.close();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fileNameDateFormat);
        String pdfFileName = String.format(pdfFileNameFormat, dtf.format(LocalDateTime.now()));

        FileDto fileDto = FileDto.builder()
                .fileName(pdfFileName)
                .rawContent(new ByteArrayInputStream(stream.toByteArray()))
                .build();
        log.info("UOM is available for download in PDF as {}", fileDto.getFileName());
        return fileDto;
    }

    @Override
    public BeanUtilsBean getBeanUtilsBean() {
        return this.beanUtilsBean;
    }
}
