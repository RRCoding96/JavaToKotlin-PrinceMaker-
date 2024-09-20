package com.makers.princemaker.exception;

import com.makers.princemaker.dto.PrinceMakerErrorResponse;
import com.makers.princemaker.code.PrinceMakerErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Snow
 */
@Slf4j
@ControllerAdvice
public class PrinceMakerExceptionHandler {
    @ExceptionHandler(PrinceMakerException.class)
    @ResponseBody
    public PrinceMakerErrorResponse handlePrinceMakerException(
            PrinceMakerException e,
            HttpServletRequest request
    ) {
        log.error("errorCode: {}, url: {}, message: {}", e.getPrinceMakerErrorCode(),
                request.getRequestURI(), e.getDetailMessage(), e);
        return PrinceMakerErrorResponse.builder()
                .errorCode(e.getPrinceMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public PrinceMakerErrorResponse handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
        return PrinceMakerErrorResponse.builder()
                .errorCode(PrinceMakerErrorCode.INVALID_REQUEST)
                .errorMessage(PrinceMakerErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PrinceMakerErrorResponse handleException(
            Exception e,
            HttpServletRequest request
    ) {
        log.error("url: {}, message: {}", request.getRequestURI(), e.getMessage(), e);
        return PrinceMakerErrorResponse.builder()
                .errorCode(PrinceMakerErrorCode.INTERNAL_SERVER_ERROR)
                .errorMessage(PrinceMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }
}
