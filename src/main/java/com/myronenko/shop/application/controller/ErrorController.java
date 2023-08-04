package com.myronenko.shop.application.controller;

import com.myronenko.shop.application.domain.dto.ErrorResponseDto;
import com.myronenko.shop.application.exception.DataAlreadyExistsException;
import com.myronenko.shop.application.exception.NoDataAvailableException;
import com.myronenko.shop.application.service.util.DateTimeGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;

@Slf4j
@ResponseBody
@ControllerAdvice
@AllArgsConstructor
public class ErrorController extends ResponseEntityExceptionHandler {

    private static final String NO_DATA_ERROR_CODE = "no_data";

    private static final String CONFLICT_ERROR_CODE = "conflict";

    private DateTimeGenerator dateTimeGenerator;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoDataAvailableException.class)
    public ErrorResponseDto handleNoDataAvailableException(NoDataAvailableException ex) {
        logExceptionCause(ex);
        return new ErrorResponseDto(ex.getMessage(), NO_DATA_ERROR_CODE, dateTimeGenerator.generateNow());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ErrorResponseDto handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        logExceptionCause(ex);
        return new ErrorResponseDto(ex.getMessage(), CONFLICT_ERROR_CODE, dateTimeGenerator.generateNow());
    }

    private void logExceptionCause(Exception ex) {
        String message = MessageFormat.format("{0} caused by {1}", ex.getClass().getSimpleName(), ex.getMessage());
        LOGGER.debug(message, ex);
    }

}
