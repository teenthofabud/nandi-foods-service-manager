package com.teenthofabud.wizard.nandifoods.wms.settings.error;

import com.teenthofabud.wizard.nandifoods.wms.error.WMSExceptionHandler;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.error.UnitException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SettingsExceptionHandler extends WMSExceptionHandler {

    @ExceptionHandler(value = { UnitException.class })
    public ResponseEntity<ErrorVo> handleCategorySubDomainExceptions(WMSBaseException e) {
        ResponseEntity<ErrorVo>  response = super.parseExceptionToResponse(e);
        return response;
    }

}