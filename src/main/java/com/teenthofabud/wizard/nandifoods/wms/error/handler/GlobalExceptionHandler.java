package com.teenthofabud.wizard.nandifoods.wms.error.handler;


import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends WMSExceptionHandler{
    @ExceptionHandler(value = { WMSBaseException.class })
    public ResponseEntity<ErrorVo> handleCategorySubDomainExceptions(WMSBaseException e) {
        ResponseEntity<ErrorVo>  response = super.parseExceptionToResponse(e);
        log.error(response.getBody().getMessage(),e);
        return response;
    }
}
