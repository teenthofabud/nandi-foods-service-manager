package com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.MetricSystem;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.MetricSystemContext;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.form.UnitClassMeasuredValuesForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMMeasuredValuesEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.form.UOMSelfLinkageForm;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMMeasuredValuesJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMSelfLinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.service.UOMService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMSelfLinkageVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.UnitClassMeasuredValuesVo;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UOMSelfLinkageEntityReducer uomSelfLinkageEntityReducer;
    private UOMSelfLinkageJpaRepository uomSelfLinkageJpaRepository;
    private UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter;


    @Autowired
    public UOMServiceImpl(UOMJpaRepository uomJpaRepository,
                          UOMFormToEntityConverter uomFormToEntityConverter,
                          UOMEntityToVoConverter uomEntityToVoConverter,
                          UnitClassMeasuredValuesFormToUOMMeasuredValuesEntityConverter unitClassToUOMMeasuredValuesConverter,
                          UOMMeasuredValuesJpaRepository uomMeasuredValuesJpaRepository,
                          UOMMeasuredValuesEntityToUnitClassMeasuredValuesVoConverter uomToUnitClassMeasuredValuesConverter,
                          ObjectMapper objectMapper,
                          UOMSelfLinkageEntityReducer uomSelfLinkageEntityReducer,
                          UOMSelfLinkageJpaRepository uomSelfLinkageJpaRepository,
                          UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter) {
        this.uomJpaRepository = uomJpaRepository;
        this.uomFormToEntityConverter = uomFormToEntityConverter;
        this.uomEntityToVoConverter = uomEntityToVoConverter;
        this.unitClassToUOMMeasuredValuesConverter = unitClassToUOMMeasuredValuesConverter;
        this.uomMeasuredValuesJpaRepository = uomMeasuredValuesJpaRepository;
        this.uomToUnitClassMeasuredValuesConverter = uomToUnitClassMeasuredValuesConverter;
        this.objectMapper = objectMapper;
        this.uomSelfLinkageEntityReducer = uomSelfLinkageEntityReducer;
        this.uomSelfLinkageJpaRepository = uomSelfLinkageJpaRepository;
        this.uomSelfLinkageEntityToVoConverter = uomSelfLinkageEntityToVoConverter;
    }

    private UOMEntity createNewUOMMeasuredValues(UOMEntity uomEntity, UnitClassMeasuredValuesForm form, MetricSystem metricSystem) {
        MetricSystemContext.set(metricSystem);
        UOMMeasuredValuesEntity uomMeasuredValuesEntity = unitClassToUOMMeasuredValuesConverter.convert(form);
        MetricSystemContext.clear();
        uomMeasuredValuesEntity.setUom(uomEntity);// because of bidirectional one to many strategy by vlad mihalcea the entire JPA relationship needs to be managed by hand
        uomMeasuredValuesEntity = uomMeasuredValuesJpaRepository.save(uomMeasuredValuesEntity);
        uomEntity.addUOMeasuredValue(uomMeasuredValuesEntity);
        uomEntity = uomJpaRepository.save(uomEntity);
        log.debug("UOM code: {} assigned with {} measured values with id: {}", uomEntity.getCode(), metricSystem, uomMeasuredValuesEntity.getId());
        return uomEntity;
    }

    private UOMEntity linkAllUOMs(UOMEntity from, List<UOMSelfLinkageForm> linkedUOMs) {
        for(UOMSelfLinkageForm e : linkedUOMs) {
            Optional<UOMEntity> to = uomJpaRepository.findByCode(e.getCode());
            if(to.isEmpty()) {
                throw new IllegalArgumentException("UOM does not exist with code: " + e.getCode());
            }
            UOMSelfLinkageEntity uomSelfLinkageEntity = uomSelfLinkageEntityReducer.reduce(e, from, to.get());
            uomSelfLinkageEntity = uomSelfLinkageJpaRepository.save(uomSelfLinkageEntity);
            from.addConversionFromUOM(uomSelfLinkageEntity);
            from.addConversionToUOM(uomSelfLinkageEntity);
            from = uomJpaRepository.save(from);
            log.debug("UOM with code: {} linked to UOM with code: {}", from.getCode(), uomSelfLinkageEntity.getToUOM().getCode());
        }
        return from;
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
        UOMEntity uomEntity = uomFormToEntityConverter.convert(form);
        uomEntity = uomJpaRepository.save(uomEntity);
        log.debug("UOM saved with id: {}", uomEntity.getId());

        // Save Imperial measure values
        uomEntity = createNewUOMMeasuredValues(uomEntity, form.getImperial(), MetricSystem.IMPERIAL);

        // Save Metric measure values
        uomEntity = createNewUOMMeasuredValues(uomEntity, form.getMetric(), MetricSystem.SI);

        // Save linked UOMs
        linkAllUOMs(uomEntity, form.getLinkedUOMs());

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
        uomVo.setImperial(getUnitClassMeasuredValuesFor(uomEntity, MetricSystem.IMPERIAL));
        uomVo.setMetric(getUnitClassMeasuredValuesFor(uomEntity, MetricSystem.SI));
        List<UOMSelfLinkageVo> uomSelfLinkageVos = uomEntity.getToUOMs().stream()
                .map(e -> uomSelfLinkageEntityToVoConverter.convert(e))
                .collect(Collectors.toList());
        uomVo.setSelfLinksTo(uomSelfLinkageVos);
        return uomVo;
    }

    @Transactional
    @Override
    public UOMVo updateExistingUOMByCode(String code, Diff dtoUpdates) {
        Optional<UOMEntity> optionalUOMEntity = uomJpaRepository.findByCode(code);
        if(optionalUOMEntity.isEmpty()) {
            throw new IllegalArgumentException("UOM does not exist with code: " + code);
        }
        dtoUpdates.getChangesByType(PropertyChange.class).forEach(p ->
                log.debug("{} changed from {} to {}", p.getPropertyNameWithPath(), p.getLeft(), p.getRight())
        );
        UOMEntity uomEntity = optionalUOMEntity.get();
        log.debug("UOM found with code: {}", uomEntity.getCode());
        /*try {
            JsonNode patched = dto.apply(objectMapper.convertValue(uomEntity, JsonNode.class));
            uomEntity = objectMapper.treeToValue(patched, UOMEntity.class);
        } catch (JsonPatchException e) {
            throw new UnsupportedOperationException("Failed to apply patch: " + e.getMessage());
            log.error("Failed to apply patch", e);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Content does not match target type: " + e.getMessage());
            log.error("Content does not match target type", e);
        }*/
        uomJpaRepository.save(uomEntity);
        return null;
    }
}
