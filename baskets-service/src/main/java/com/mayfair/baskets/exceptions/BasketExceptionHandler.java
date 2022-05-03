package com.mayfair.baskets.exceptions;

import com.mayfair.baskets.constants.BsErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class BasketExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BasketException.class)
    public ResponseEntity<BsExceptionDetails> handlePaymentException(BasketException exception) {
        log.error("Failed Operation", exception);
        BsExceptionDetails exceptionDetails = exception.getExceptionDetails();
      if(BsErrorCodes.TEMPORARY_FAILURE.getCode().equals(exceptionDetails.getErrorCode())) {
             return new ResponseEntity(exceptionDetails, HttpStatus.SERVICE_UNAVAILABLE);
         }else{
             return new ResponseEntity(exceptionDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Binding Exception", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        String errorSummary=errors!=null?errors.toString():ex.getMessage();
        BsExceptionDetails response=new BsExceptionDetails("Binding Exception",errorSummary);
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.error("Binding Exception", ex);
        BsExceptionDetails response=new BsExceptionDetails("Binding Exception",ex.getMessage());
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BsExceptionDetails> handleUnexpected(Exception ex) {
        log.error("Unexpected exception", ex);
        BsExceptionDetails error = new BsExceptionDetails(ex.getClass().getName(), ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
