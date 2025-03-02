package com.teenthofabud.wizard.nandifoods.wms.settings.unit.reducer;

import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.UnitClassSelfLinkageDtoV2;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMSelfLinkageEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMSelfLinkageJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.type.CollectionItemDtoToEntityReducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UnitClassSelfLinkageDtoV2ToUOMSelfLinkageEntityReducer implements CollectionItemDtoToEntityReducer<UnitClassSelfLinkageDtoV2, UOMSelfLinkageEntity> {

    @Autowired
    UOMSelfLinkageJpaRepository uomSelfLinkageJpaRepository;
    @Autowired
    UOMJpaRepository uomJpaRepository;


    @Override
    public UOMSelfLinkageEntity reduce(UnitClassSelfLinkageDtoV2 source) {
        log.debug("source : {}",source);
        UOMSelfLinkageEntity selfLinkageEntity = null;
        Optional<UOMEntity> optionalFrom = uomJpaRepository.findByCode(source.getFromCode());
        UOMEntity from = null;
        Optional<UOMEntity> optionalTo = uomJpaRepository.findByCode(source.getCode());
        UOMEntity to = null;
        if(optionalFrom.isPresent() && optionalTo.isPresent()){
            from = optionalFrom.get();
            to = optionalTo.get();
            selfLinkageEntity = uomSelfLinkageJpaRepository.findByFromUomAndToUom(from,to);
            if(selfLinkageEntity!=null){
                log.debug("SelfLinkageEntity found in db; from : {}; to : {}",selfLinkageEntity.getFromUom().getCode(),selfLinkageEntity.getToUom().getCode());
                selfLinkageEntity.setToUom(to);
                selfLinkageEntity.setQuantity(source.getQuantity());
            }
        }
        if(selfLinkageEntity==null){
            log.debug("SelfLinkageEntity not found in db");
            selfLinkageEntity = UOMSelfLinkageEntity.builder()
                    .toUom(to)
                    .fromUom(from)
                    .quantity(source.getQuantity())
                    .build();
        }
        log.debug("reduced selfLinkageEntity -> from : {}, to : {}",selfLinkageEntity.getFromUom().getCode(),selfLinkageEntity.getToUom().getCode());
        return selfLinkageEntity;
    }
}
