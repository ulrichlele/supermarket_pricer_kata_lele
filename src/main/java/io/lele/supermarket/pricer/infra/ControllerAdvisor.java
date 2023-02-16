package io.lele.supermarket.pricer.infra;

import io.lele.supermarket.pricer.core.BaseAppException;
import io.lele.supermarket.pricer.core.RecordNotFound;
import io.lele.supermarket.pricer.core.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static final String ERR_FIELD_MESSAGE = "message";
    private static final String ERR_FIELD_TIMESTAMP = "timestamp";
    private static final String ERR_FIELD_DATA = "data";
    private static final String VALIDATION_ERROR_MESSAGE = "VALIDATION_ERROR";
    private static final String MESSAGE_RECORD_NO_FOUND = "RECORD_NO_FOUND";



    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptionException(
            ValidationException ex, WebRequest request) {
        Map<String, Object> body = buildErrorBody(VALIDATION_ERROR_MESSAGE, ex.getValidatorResults());
        return new ResponseEntity<>(body,  new HttpHeaders(), HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(RecordNotFound.class)
    public ResponseEntity<Object> handleRecordNotFoundException(
            RecordNotFound ex, WebRequest request) {
        Map<String, Object> body = buildErrorBody(MESSAGE_RECORD_NO_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BaseAppException.class)
    public ResponseEntity<Object> handleBaseAppException(
            BaseAppException ex, WebRequest request) {
        Map<String, Object> body = buildErrorBody(ex.getClass().getSimpleName());
        return new ResponseEntity<>(body, HttpStatus.PARTIAL_CONTENT);
    }

    private  Map<String, Object> buildErrorBody(String message, Object data){
        Map<String, Object> body = buildErrorBody(message);
        body.put(ERR_FIELD_DATA, data);
        return body;
    }

    private  Map<String, Object> buildErrorBody(String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERR_FIELD_TIMESTAMP, LocalDateTime.now());
        body.put(ERR_FIELD_MESSAGE, message);
        return body;
    }
}