package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.impl;

import com.diffplug.common.base.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
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
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer.UnitClassSelfLinkageToUOMSelfLinkageEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
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
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMSelfLinkageVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UOMServiceImpl implements UOMService {

    private UOMJpaRepository uomJpaRepository;
    private UOMFormToEntityConverter uomFormToEntityConverter;
    private UOMEntityToVoConverter uomEntityToVoConverter;
    private UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter unitClassToUOMMeasuredValuesConverter;
    private UOMMeasuredValuesJpaRepository uomMeasuredValuesJpaRepository;
    private UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomToUnitClassMeasuredValuesConverter;
    private ObjectMapper objectMapper;
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
    //private UOMSummaryProjectionRepository uomSummaryProjectionRepository;

    private List<String> searchFields;
    private String fileNameDateFormat;
    private String csvFileNameFormat;

    @Autowired
    public UOMServiceImpl(UOMJpaRepository uomJpaRepository,
                          UOMFormToEntityConverter uomFormToEntityConverter,
                          UOMEntityToVoConverter uomEntityToVoConverter,
                          UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter unitClassToUOMMeasuredValuesConverter,
                          UOMMeasuredValuesJpaRepository uomMeasuredValuesJpaRepository,
                          UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomToUnitClassMeasuredValuesConverter,
                          ObjectMapper objectMapper,
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
                          //UOMSummaryProjectionRepository uomSummaryProjectionRepository,
                          @Value("#{'${wms.settings.uom.search.fields}'.split(',')}") List<String> searchFields,
                          @Value("${wms.settings.unit.fileNameDateTimeFormat}") String fileNameDateFormat,
                          @Value("${wms.settings.uom.fileNameFormat.csv}") String csvFileNameFormat) {
        this.uomJpaRepository = uomJpaRepository;
        this.uomFormToEntityConverter = uomFormToEntityConverter;
        this.uomEntityToVoConverter = uomEntityToVoConverter;
        this.unitClassToUOMMeasuredValuesConverter = unitClassToUOMMeasuredValuesConverter;
        this.uomMeasuredValuesJpaRepository = uomMeasuredValuesJpaRepository;
        this.uomToUnitClassMeasuredValuesConverter = uomToUnitClassMeasuredValuesConverter;
        this.objectMapper = objectMapper;
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
        this.searchFields = searchFields;
        this.fileNameDateFormat = fileNameDateFormat;
        this.csvFileNameFormat = csvFileNameFormat;
        //this.uomSummaryProjectionRepository = uomSummaryProjectionRepository;
    }

    /*private UOMEntity createNewUOMMeasuredValues(UOMEntity uomEntity, UnitClassMeasuredValuesForm form, MetricSystem metricSystem) {
        MetricSystemContext.set(metricSystem);
        UOMMeasuredValuesEntity uomMeasuredValuesEntity = unitClassToUOMMeasuredValuesConverter.convert(form);
        MetricSystemContext.clear();
        uomMeasuredValuesEntity.setUom(uomEntity);// because of bidirectional one to many strategy by vlad mihalcea the entire JPA relationship needs to be managed by hand
        uomMeasuredValuesEntity = uomMeasuredValuesJpaRepository.save(uomMeasuredValuesEntity);
        uomEntity.addUOMeasuredValue(uomMeasuredValuesEntity);
        uomEntity = uomJpaRepository.save(uomEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), metricSystem, uomMeasuredValuesEntity.getId());
        return uomEntity;
    }*/

    private void createNewUOMMeasuredValues(UOMEntity uomEntity, UnitClassMeasuredValuesForm form) {
        UOMMeasuredValuesEntity uomMeasuredValuesEntity = unitClassToUOMMeasuredValuesConverter.convert(form);
        uomMeasuredValuesEntity.setUom(uomEntity);// because of bidirectional one to many strategy by vlad mihalcea the entire JPA relationship needs to be managed by hand
        uomEntity.addUOMeasuredValue(uomMeasuredValuesEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), form.getMetricSystem(), uomMeasuredValuesEntity.getId());
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
            from.addConversionToUOM(uomSelfLinkageEntity);
            log.debug("UOM with code: {} linked to UOM with code: {}", from.getCode(), uomSelfLinkageEntity.getToUOM().getCode());
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
        linkedPUHUs.stream().filter(e -> e.getType().compareTo(UnitClassType.PU) == 0).forEach(e -> puLink(from, e));
        linkedPUHUs.stream().filter(e -> e.getType().compareTo(UnitClassType.HU) == 0).forEach(e -> huLink(from, e));
    }

    private UnitClassMeasuredValuesVo getUnitClassMeasuredValuesFor(UOMEntity uomEntity, MetricSystem metricSystem) {
        UnitClassMeasuredValuesVo unitClassMeasuredValuesVo = uomEntity.getUomMeasuredValues()
                .stream()
                .filter(umv -> umv.getMetricSystem().compareTo(metricSystem) == 0)
                .map(f -> uomToUnitClassMeasuredValuesConverter.convert(f))
                .findFirst()
                .get();
        return unitClassMeasuredValuesVo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UOMVo createNewUOM(UOMForm form) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(form.getCode());
        if(optionalUOMEntity.isPresent()) {
            throw new IllegalStateException("UOM already exists with id: " + form.getCode());
        }

        // Save UOM
        final UOMEntity uomEntity = uomFormToEntityConverter.convert(form);

        // Save measured values for all metric systems
        form.getMeasuredValues().stream().forEach(f -> createNewUOMMeasuredValues(uomEntity, f));

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
        List<UOMSelfLinkageVo> fromVos = uomEntity.getFromUOMs().stream()
                .map(e -> uomSelfLinkageEntityToVoConverter.convert(e))
                .collect(Collectors.toList());
        UOMSelfLinkageContext.setCascadeLevelContext(true);
        List<UOMSelfLinkageVo> toVos = uomEntity.getToUOMs().stream()
                .map(e -> uomSelfLinkageEntityToVoConverter.convert(e))
                .collect(Collectors.toList());
        UOMSelfLinkageContext.clearCascadeLevelContext();
        List<UOMSelfLinkageVo> uomSelfLinkageVos = Stream.concat(fromVos.stream(), toVos.stream()).collect(Collectors.toList());
        uomVo.setSelfLinksTo(uomSelfLinkageVos);
        return uomVo;
    }

    private void unlinkSelf(UOMEntity from) {
        List<UOMSelfLinkageEntity> fromUOMs = new CopyOnWriteArrayList<>(from.getFromUOMs());
        fromUOMs.stream().forEach(f -> {
            f.setFromUOM(null);
            f.setToUOM(null);
        });
        fromUOMs.stream().forEach(f -> from.removeConversionFromUOM(f));
        log.debug("Removed all UOM links that UOM with code: {} is related to", from.getCode());
        //fromUOMs.stream().forEach(f -> uomSelfLinkageJpaRepository.delete(f));
        List<UOMSelfLinkageEntity> toUOMs = new CopyOnWriteArrayList<>(from.getToUOMs());
        toUOMs.stream().forEach(f -> {
            f.setFromUOM(null);
            f.setToUOM(null);
        });
        toUOMs.stream().forEach(f -> from.removeConversionToUOM(f));
        log.debug("Removed all UOM links that UOM with code: {} is related with", from.getCode());
        //toUOMs.stream().forEach(f -> uomSelfLinkageJpaRepository.delete(f));
        //uomJpaRepository.save(from);
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
        // unlink measured values, self, pu hu
        unlinkSelf(uomEntity);
        unlinkCross(uomEntity);
        uomJpaRepository.delete(uomEntity);
        log.debug("UOM deleted with code: {}", uomEntity.getCode());
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveAllUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto) {
        Pageable pageable = uomPageDtoToPageableConverter.convert(uomPageDto);
        Page<UOMEntity> uomEntityPage = new PageImpl<>(List.of());
        if(optionalLongName.isPresent()) {
            String longName = optionalLongName.get();
            Specification<UOMEntity> uomSearchQuerySpecification = uomJpaRepository.likeProperty("longName", longName);
            log.debug("Created specification for UOMEntity using long name: {}", longName);
            uomEntityPage = uomJpaRepository.findAll(uomSearchQuerySpecification, pageable);
        } else {
            log.debug("Searching UOMEntity with page: {}", pageable);
            uomEntityPage = uomJpaRepository.findAll(pageable);
        }
        List<UOMVo> uomVoList = uomEntityPage.stream().map(i -> {
            List<UOMSelfLinkageVo> uomSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(uomSelfLinkageVoList);
            return uomVo;
        }).collect(Collectors.toList());
        UOMPageImplVo uomPageImplVo = new UOMPageImplVo(uomVoList, uomEntityPage.getPageable(), uomEntityPage.getTotalElements());
        log.debug("Found {} UOM in page {}", uomEntityPage.getTotalElements(), uomPageImplVo.getNumber());
        return uomPageImplVo;
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveAllUOMWithinRange(Optional<String> optionalQuery, @Valid UOMPageDto uomPageDto) {
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
            List<UOMSelfLinkageVo> uomSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(uomSelfLinkageVoList);
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
            rawDtoUpdates.getChanges().forEach(Errors.rethrow().wrap(p -> {
                log.debug("{}", p);
            }));
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
        //Writer writer = new StringWriter();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        //List<UOMSummaryProjection> uomSummaryList = uomSummaryProjectionRepository.findAllWithMeasuredValueForMetricSystem(MetricSystem.SI);
        //List<UOMSummaryVo> uomSummaryVoList = uomSummaryList.stream().map(f -> uomSummaryProjectionToVoConverter.convert(f)).collect(Collectors.toList());
        StatefulBeanToCsv<UOMVo> sbc = new StatefulBeanToCsvBuilder<UOMVo>(writer)
        //StatefulBeanToCsv<UOMSummaryVo> sbc = new StatefulBeanToCsvBuilder<UOMSummaryVo>(writer)
                .withQuotechar('\'')
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fileNameDateFormat);
        String csvFileName = String.format(csvFileNameFormat, dtf.format(LocalDateTime.now()));
        try {
            sbc.write(uomVoList);
            //sbc.write(uomSummaryVoList);
            streamWriter.flush();
            FileDto fileDto = FileDto.builder()
                    .fileName(csvFileName)
                    .rawContent(new ByteArrayInputStream(stream.toByteArray()))
                    .build();
            return fileDto;
        } catch (CsvDataTypeMismatchException e) {
            throw new IllegalArgumentException("CSV bean field not configured properly", e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new IllegalArgumentException("CSV bean field is empty", e);
        }
    }
}
