package com.teenthofabud.wizard.nandifoods.wms.settings.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ErrorVo> handleIOException(IOException e) {
        String message = "Unable to process output";
        log.error(message, e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(message + ": " + e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorVo> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorVo);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ErrorVo> handleIllegalStateException(IllegalStateException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorVo);
    }

    @ExceptionHandler(value = IllegalAccessException.class)
    public ResponseEntity<ErrorVo> handleIllegalAccessException(IllegalAccessException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }

    @ExceptionHandler(value = InvocationTargetException.class)
    public ResponseEntity<ErrorVo> handleInvocationTargetException(InvocationTargetException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorVo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Unable to apply patch to entity via dto", e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors().stream().collect(Collectors.toList());
        ErrorVo errorVo = ErrorVo.builder()
                .message(errors.get(0).getDefaultMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorVo);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorVo> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Unable to apply patch to entity via dto", e);
        List<ConstraintViolation<?>> errors = e.getConstraintViolations().stream().collect(Collectors.toList());
        ErrorVo errorVo = ErrorVo.builder()
                .message(errors.get(0).getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVo);
    }

    @ExceptionHandler(value = JsonPatchException.class)
    public ResponseEntity<ErrorVo> handleJsonPatchException(JsonPatchException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVo);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<ErrorVo> handleJsonProcessingException(JsonProcessingException e) {
        log.error("Unable to apply patch to entity via dto", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    public ResponseEntity<ErrorVo> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("Unable to perform operation", e);
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }
}
