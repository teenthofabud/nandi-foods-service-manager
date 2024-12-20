package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.impl;

import com.diffplug.common.base.Errors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.teenthofabud.wizard.nandifoods.wms.audit.Audit;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassType;
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
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UOMPageDtoToPageableConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.MetricSystemContext;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UOMEntityToVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UOMFormToEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer.UnitClassSelfLinkageToUOMSelfLinkageEntityReducer;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UOMSelfLinkageEntityToVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.CSVDto;
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
import jakarta.validation.constraints.NotBlank;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<String> searchFields;

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
                          @Value("#{'${wms.settings.uom.search.fields}'.split(',')}") List<String> searchFields) {
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
        this.searchFields = searchFields;
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
        uomMeasuredValuesEntity = uomMeasuredValuesJpaRepository.save(uomMeasuredValuesEntity);
        uomEntity.addUOMeasuredValue(uomMeasuredValuesEntity);
        uomEntity = uomJpaRepository.save(uomEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), form.getMetricSystem(), uomMeasuredValuesEntity.getId());
    }

    private void selfLink(UOMEntity from, Optional<List<UnitClassSelfLinkageForm>> optionalUnitClassSelfLinkageForms) {
        if(optionalUnitClassSelfLinkageForms.isEmpty()) {
            log.debug("No UOMs linked! Skipping UOM self linkage logic");
            return;
        }
        for(UnitClassSelfLinkageForm e : optionalUnitClassSelfLinkageForms.get()) {
            Optional<UOMEntity> to = uomJpaRepository.findByCode(e.getCode());
            if(to.isEmpty()) {
                throw new IllegalArgumentException("UOM does not exist with code: " + e.getCode());
            }
            UOMSelfLinkageEntity uomSelfLinkageEntity = unitClassSelfLinkageToUOMSelfLinkageEntityReducer.reduce(e, from, to.get());
            uomSelfLinkageEntity = uomSelfLinkageJpaRepository.save(uomSelfLinkageEntity);
            from.addConversionFromUOM(uomSelfLinkageEntity);
            from.addConversionToUOM(uomSelfLinkageEntity);
            from = uomJpaRepository.save(from);
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
        uomPULinkageEntity = uomPULinkageJpaRepository.save(uomPULinkageEntity);
        from.addUOMPULinkage(uomPULinkageEntity);
        from = uomJpaRepository.save(from);
        log.debug("UOM with code: {} linked to PU with code: {}", from.getCode(), to.getCode());
    }

    private void huLink(UOMEntity from, UnitClassCrossLinkageForm e) {
        Optional<HUEntity> optionalTo = huRepository.findByCode(e.getCode());
        if(optionalTo.isEmpty()) {
            throw new IllegalArgumentException("HU does not exist with code: " + e.getCode());
        }
        HUEntity to = optionalTo.get();
        UOMHULinkageEntity uomHULinkageEntity = unitClassCrossLinkageToUOMHULinkageEntityReducer.reduce(e, from, to);
        uomHULinkageEntity = uomHULinkageJpaRepository.save(uomHULinkageEntity);
        from.addUOMHULinkage(uomHULinkageEntity);
        from = uomJpaRepository.save(from);
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

    @Transactional
    @Override
    public UOMVo createNewUOM(UOMForm form) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(form.getCode());
        if(optionalUOMEntity.isPresent()) {
            throw new IllegalStateException("UOM already exists with id: " + form.getCode());
        }

        // Save UOM
        final UOMEntity uomEntity = uomFormToEntityConverter.convert(form);
        uomJpaRepository.save(uomEntity);
        log.debug("UOM saved with id: {}", uomEntity.getId());

        // Save measured values for all metric systems
        form.getMeasuredValues().stream().forEach(f -> createNewUOMMeasuredValues(uomEntity, f));

        // Save linked UOMs
        selfLink(uomEntity, form.getLinkedUOMs());

        // Save linked PU/HU
        crossLink(uomEntity, form.getLinkedPUHUs());

        // Prepare UOM for display
        UOMVo uomVo = uomEntityToVoConverter.convert(uomEntity);
        log.info("Saved UOMEntity with id: {}", uomEntity.getId());
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
        List<UOMSelfLinkageVo> uomSelfLinkageVos = uomEntity.getFromUOMs().stream()
                .map(e -> uomSelfLinkageEntityToVoConverter.convert(e))
                .collect(Collectors.toList());
        uomVo.setSelfLinksTo(uomSelfLinkageVos);
        return uomVo;
    }

    @Transactional
    @Override
    public void deleteExistingUOMByCode(String code) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        UOMEntity uomEntity = optionalUOMEntity.get();
        log.debug("UOM found with code: {}", uomEntity.getCode());
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
    public CSVDto downloadAllUOMInCSV() throws IOException {
        //Writer writer = new StringWriter();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        StatefulBeanToCsv<UOMVo> sbc = new StatefulBeanToCsvBuilder<UOMVo>(writer)
                .withQuotechar('\'')
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        try {
            sbc.write(uomVoList);
            streamWriter.flush();
            CSVDto csvDto = CSVDto.builder()
                    .fileName("UOMList.csv")
                    .rawContent(stream.toByteArray())
                    .build();
            return csvDto;
        } catch (CsvDataTypeMismatchException e) {
            throw new IllegalArgumentException("CSV bean field not configured properly", e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new IllegalArgumentException("CSV bean field is empty", e);
        }
    }
}
