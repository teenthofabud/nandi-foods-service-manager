package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.impl;

import com.diffplug.common.base.Errors;
import com.teenthofabud.wizard.nandifoods.wms.handler.ComparativeUpdateHandler;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MeasurementSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClass;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassMeasuredValuesDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
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
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.converter.UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.type.UnitClassSelfLinkageContract;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMMeasuredValuesJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMSelfLinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassSelfLinkageVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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
    private BeanUtilsBean beanUtilsBean;
    private UnitClassCrossLinkageToUOMPULinkageEntityReducer unitClassCrossLinkageToUOMPULinkageEntityReducer;
    private UnitClassCrossLinkageToUOMHULinkageEntityReducer unitClassCrossLinkageToUOMHULinkageEntityReducer;
    private HURepository huRepository;
    private PURepository puRepository;
    private UOMPULinkageJpaRepository uomPULinkageJpaRepository;
    private UOMHULinkageJpaRepository uomHULinkageJpaRepository;
    private Javers javers;
    private UOMEntityToDtoV2Converter uomEntityToDtoV2Converter;
    private UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter;
    private UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter;
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
                          BeanUtilsBean beanUtilsBean,
                          UnitClassCrossLinkageToUOMPULinkageEntityReducer unitClassCrossLinkageToUOMPULinkageEntityReducer,
                          HURepository huRepository,
                          PURepository puRepository,
                          UnitClassCrossLinkageToUOMHULinkageEntityReducer unitClassCrossLinkageToUOMHULinkageEntityReducer,
                          UOMPULinkageJpaRepository uomPULinkageJpaRepository,
                          UOMHULinkageJpaRepository uomHULinkageJpaRepository,
                          Javers javers,
                          UOMEntityToDtoV2Converter uomEntityToDtoV2Converter,
                          UOMSelfLinkageEntityToUnitClassSelfLinkageVoConverter uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter,
                          UOMSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter,
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
        this.beanUtilsBean = beanUtilsBean;
        this.unitClassCrossLinkageToUOMPULinkageEntityReducer = unitClassCrossLinkageToUOMPULinkageEntityReducer;
        this.huRepository = huRepository;
        this.puRepository = puRepository;
        this.unitClassCrossLinkageToUOMHULinkageEntityReducer = unitClassCrossLinkageToUOMHULinkageEntityReducer;
        this.uomPULinkageJpaRepository = uomPULinkageJpaRepository;
        this.uomHULinkageJpaRepository = uomHULinkageJpaRepository;
        this.javers = javers;
        this.uomEntityToDtoV2Converter = uomEntityToDtoV2Converter;
        this.searchFields = searchFields;
        this.fileNameDateFormat = fileNameDateFormat;
        this.csvFileNameFormat = csvFileNameFormat;
        this.pdfFileNameFormat = pdfFileNameFormat;
        this.uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter = uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter;
        this.uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter = uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter;
        //this.uomSummaryProjectionRepository = uomSummaryProjectionRepository;
    }

    private void createNewUOMMeasuredValues(UOMEntity uomEntity, UnitClassMeasuredValuesForm form) {
        UOMMeasuredValuesEntity uomMeasuredValuesEntity = unitClassToUOMMeasuredValuesConverter.convert(form);
        uomMeasuredValuesEntity.setUom(uomEntity);// because of bidirectional one to many strategy by vlad mihalcea the entire JPA relationship needs to be managed by hand
        uomEntity.addMeasuredValue(uomMeasuredValuesEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), form.getMeasurementSystem(), uomMeasuredValuesEntity.getId());
    }

    private void selfLink(UOMEntity from, Optional<List<? extends UnitClassSelfLinkageContract>> optionalUnitClassSelfLinkageCollection) {
        if(optionalUnitClassSelfLinkageCollection.isEmpty()) {
            log.debug("No UOMs linked! Skipping UOM self linkage logic");
            return;
        }
        for(UnitClassSelfLinkageContract e : optionalUnitClassSelfLinkageCollection.get()) {
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

    @Transactional
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
        selfLink(uomEntity, form.getLinkedUOMs().isEmpty() ? Optional.empty() : Optional.of( new ArrayList<>(form.getLinkedUOMs().get())));

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
        log.debug("Retrieved all linked UOMs to this UOM with code: {}", uomEntity.getCode());
        List<UnitClassSelfLinkageVo> fromVos = uomEntity.getFromUOMs().stream().map(e -> uomSelfLinkageEntityToUnitClassSelfLinkageVoConverter.convert(e)).collect(Collectors.toList());
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

    @Transactional
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
    public void updateExistingUOMByCode(String code, UOMDtoV2 sourceUOMDto) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        log.debug("UOM does exists with code: {}", code);
        UOMEntity uomEntity = optionalUOMEntity.get();
        UOMDtoV2 targetUOMDto = uomEntityToDtoV2Converter.convert(uomEntity);
        List<UnitClassSelfLinkageDtoV2> linkedUOMs = uomEntity.getFromUOMs().stream().map(f -> uomSelfLinkageEntityToUnitClassSelfLinkageDtoV2Converter.convert(f)).collect(Collectors.toList());
        targetUOMDto.setLinkedUOMs(Optional.of(linkedUOMs));
        Diff dtoDiff = javers.compare(targetUOMDto, sourceUOMDto);
        uomEntity = comparativelyUpdateMandatoryFields(dtoDiff, uomEntity, true);
        uomEntity = comparativelyUpdateMandatoryCollection(targetUOMDto, sourceUOMDto, uomEntity);
        uomJpaRepository.save(uomEntity);
        log.info("Updated UOMEntity with id: {}", uomEntity.getId());
    }

    private UOMEntity comparativelyUpdateMandatoryCollection(UOMDtoV2 old, UOMDtoV2 _new, UOMEntity target) {
        target = updateLinkedUOMs(old, _new, target);
        return target;
    }

    private UOMEntity updateMeasuredValues(UOMDtoV2 old,UOMDtoV2 _new, UOMEntity target) {
        List<UnitClassMeasuredValuesDtoV2> oldMeasuredValues = old.getMeasuredValues();
        List<UnitClassMeasuredValuesDtoV2> newMeasuredValues = _new.getMeasuredValues();
        oldMeasuredValues.sort((a,b)->a.getMeasurementSystem().getOrdinal() < b.getMeasurementSystem().getOrdinal() ? -1 : 1);
        newMeasuredValues.sort((a,b)->a.getMeasurementSystem().getOrdinal() < b.getMeasurementSystem().getOrdinal() ? -1 : 1);
        target.getMeasuredValues().sort((a,b)->a.getMeasurementSystem().getOrdinal() < b.getMeasurementSystem().getOrdinal() ? -1 : 1);
        Diff diff = javers.compareCollections(oldMeasuredValues,newMeasuredValues,UnitClassMeasuredValuesDtoV2.class);
        log.debug("measured values diff : {}",diff.prettyPrint());
        diff.getChangesByType(PropertyChange.class).forEach(Errors.rethrow().wrap(p -> {
            log.debug("{} changed from {} to {}", p.getPropertyNameWithPath(), p.getLeft(), p.getRight());
            String propPath = p.getPropertyNameWithPath();
            int currentIndex = Integer.parseInt(propPath.substring(propPath.indexOf("/")+1,propPath.indexOf("/")+2));
            beanUtilsBean.copyProperty(target.getMeasuredValues().get(currentIndex),p.getPropertyName(),p.getRight());
        }));
        return target;
    }


    private UOMEntity updateLinkedUOMs(UOMDtoV2 old, UOMDtoV2 _new, UOMEntity target) {
        List<UnitClassSelfLinkageDtoV2> oldLinkedUOMs = old.getLinkedUOMs().orElse(Collections.emptyList());
        List<UnitClassSelfLinkageDtoV2> newLinkedUOMs = _new.getLinkedUOMs().orElse(Collections.emptyList());
        if(oldLinkedUOMs.size() > 0 && newLinkedUOMs.size() == 0) {
            log.debug("clearing all linkedUOMs");
            target.removeLinkedFromUOMs();
        } else if(oldLinkedUOMs.size() == 0 && newLinkedUOMs.size() > 0) {
            log.debug("adding all linkedUOMs");
            List<UnitClassSelfLinkageContract> selfLinkageContracts = new ArrayList<>(newLinkedUOMs);
            selfLink(target, Optional.of(selfLinkageContracts));

        }
        else if(oldLinkedUOMs.size() > 0 && newLinkedUOMs.size() > 0) {

            Diff diffCollections = javers.compareCollections(oldLinkedUOMs, newLinkedUOMs, UnitClassSelfLinkageDtoV2.class);
            log.debug(diffCollections.prettyPrint());
            diffCollections.getChangesByType(ValueChange.class).forEach(Errors.rethrow().wrap(p -> {

                if(!(p instanceof InitialValueChange || p instanceof TerminalValueChange) ){
                    if(p.getPropertyName().equals("quantity")) {
                        log.debug("{} changed from {} to {}", p.getPropertyNameWithPath(), p.getLeft(), p.getRight());
                        int newQuantity = Integer.parseInt(p.getRight().toString());
                        String code = ((UnitClassSelfLinkageDtoV2) p.getAffectedObject().get()).getCode();
                        target.getFromUOMs().forEach(e -> {
                            if (e.getToUom().getCode().equals(code)) {
                                e.setQuantity(newQuantity);
                            }
                        });
                    }
                }
            }));

            diffCollections.getChangesByType(NewObject.class).forEach(Errors.rethrow().wrap(p->{
                UnitClassSelfLinkageDtoV2 elt = (UnitClassSelfLinkageDtoV2) p.getAffectedObject().get();
                log.debug("new linkedUOM : {}", elt.getCode());
                Optional<UOMEntity> optionalTo = uomJpaRepository.findByCode(elt.getCode());
                if(optionalTo.isPresent()){
                    UOMEntity to = optionalTo.get();
                    UOMSelfLinkageEntity selfLinkage = UOMSelfLinkageEntity.builder()
                            .fromUom(target)
                            .toUom(to)
                            .quantity(elt.getQuantity())
                            .build();
                    target.addConversionFromUOM(selfLinkage);
                }
            }));

            diffCollections.getChangesByType(ObjectRemoved.class).forEach(Errors.rethrow().wrap(p->{
                UnitClassSelfLinkageDtoV2 elt = (UnitClassSelfLinkageDtoV2) p.getAffectedObject().get();
                List<UOMSelfLinkageEntity> linkedUOMsRef = target.getFromUOMs();
                log.debug("removed linkedUOM : {}",elt.getCode());
                int toRemove = -1;
                for(int i = 0; i < linkedUOMsRef.size(); i++){
                    if(linkedUOMsRef.get(i).getToUom().getCode().equals(elt.getCode())){
                        toRemove = i;
                        break;
                    }
                }
                if(toRemove!=-1)
                    linkedUOMsRef.remove(toRemove);
            }));
        }
        return target;
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

    @Override
    public BeanUtilsBean getBeanUtilsBean() {
        return this.beanUtilsBean;
    }
}
