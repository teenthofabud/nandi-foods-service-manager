package com.teenthofabud.wizard.nandifoods.wms.error;

import com.teenthofabud.wizard.nandifoods.wms.error.core.WMSBaseException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Locale;
@Slf4j
public abstract class WMSExceptionHandler {

    @Autowired
    public void setMessageSource(WMSMessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageSource.setDefaultEncoding("UTF-8");
        this.messageSource.setBasename("messages");
    }

    private WMSMessageSource messageSource;

    public ResponseEntity<ErrorVo> parseExceptionToResponse(WMSBaseException e) {
        ErrorVo vo = new ErrorVo();
        log.debug("{}",e.getParameters());
        log.debug("{}",e.getSubDomain());
        log.debug("{}",e.getError());
        String msg = this.messageSource.getMessage(e.getError().getErrorCode(), (Object[])null, Locale.getDefault());
        if (e.getParameters() != null) {
            Deque<Object> parameters = new ArrayDeque(Arrays.asList(e.getParameters()));
            msg = String.format(msg, parameters.toArray(new Object[parameters.size()]));
        }

        String domainAndSubDomain = String.join(" - ", Arrays.asList(e.getError().getDomain(), e.getSubDomain()));
        vo.setErrorCode(e.getError().getErrorCode());
        vo.setMessage(msg);
        vo.setDomain(domainAndSubDomain);
//        vo.setTraceId(this.tracer.currentSpan().context().traceIdString());
        return ResponseEntity.status(e.getError().getHttpStatusCode()).body(vo);
    }


}
