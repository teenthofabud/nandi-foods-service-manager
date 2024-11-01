package com.teenthofabud.wizard.nandifoods.wms.settings.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.vo.ErrorVo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorVo> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorVo);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ErrorVo> handleIllegalStateException(IllegalStateException e) {
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorVo);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorVo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors().stream().collect(Collectors.toList());
        ErrorVo errorVo = ErrorVo.builder()
                .message(errors.get(0).getDefaultMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorVo);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorVo> handleConstraintViolationException(ConstraintViolationException e) {
        List<ConstraintViolation<?>> errors = e.getConstraintViolations().stream().collect(Collectors.toList());
        ErrorVo errorVo = ErrorVo.builder()
                .message(errors.get(0).getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVo);
    }

    @ExceptionHandler(value = JsonPatchException.class)
    public ResponseEntity<ErrorVo> handleJsonPatchException(JsonPatchException e) {
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorVo);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<ErrorVo> handleJsonProcessingException(JsonProcessingException e) {
        ErrorVo errorVo = ErrorVo.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorVo);
    }

}
