package com.teenthofabud.wizard.nandifoods.wms.error.handler;

import com.teenthofabud.wizard.nandifoods.wms.error.converter.WMSExceptionToErrorVoConverter;
import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WMSExceptionHandler {

    @Autowired
    private WMSExceptionToErrorVoConverter wmsExceptionToErrorVoConverter;

    @ExceptionHandler(value = { WMSBaseException.class })
    public ResponseEntity<ErrorVo> handleCategorySubDomainExceptions(WMSBaseException e) {
        ResponseEntity<ErrorVo>  response = parseExceptionToResponse(e);
        log.error(response.getBody().getMessage(),e);
        return response;
    }

    public ResponseEntity<ErrorVo> parseExceptionToResponse(WMSBaseException e) {

        ErrorVo vo = wmsExceptionToErrorVoConverter.convert(e);
        log.debug("{}",vo);
//        vo.setTraceId(this.tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(e.getError().getHttpStatusCode()).body(vo);
    }


}
