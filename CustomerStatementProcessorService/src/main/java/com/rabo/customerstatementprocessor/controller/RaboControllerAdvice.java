package com.rabo.customerstatementprocessor.controller;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.INTERNAL_SERVER_ERROR_CODE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboBadRequestException;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;
import com.rabo.customerstatementprocessor.model.ErrorResponse;

/**
 * This class is used to handle instances of Exception subclasses thrown from
 * any of the controllers in this service.
 * 
 * It also logs exceptions so that log statements for exceptions aren't
 * necessary (but can be present) in the specific layers.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 */
@RestControllerAdvice
public class RaboControllerAdvice
{
    private static final Logger       LOGGER = LoggerFactory.getLogger(RaboControllerAdvice.class);

    private final RaboMessageResolver messageResolver;

    public RaboControllerAdvice(RaboMessageResolver messageResolver)
    {
        this.messageResolver = messageResolver;
    }

    /**
     * This method is used to handle instances of RaboBadRequestException
     * subclasses thrown from the service or other layers.
     *
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(RaboBadRequestException exception)
    {
        LOGGER.warn("A bad request exception has occurred.", exception);
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    /**
     * It handles instances of RaboSystemException
     * subclasses thrown from the service or other layers.
     *
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleSystemException(RaboSystemException exception)
    {
        LOGGER.error("An system exception has occurred.", exception);
        return new ErrorResponse(exception.getCode(), exception.getMessage());
    }

    /**
     * It handles instances of uncaught Runtime exceptions (other than
     * Rabo-specific) exceptions thrown from the service or other layers.
     *
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUncaughtRuntimeException(RuntimeException exception)
    {
        LOGGER.error("An exception has occurred.", exception);
        String code = INTERNAL_SERVER_ERROR_CODE;
        String message = messageResolver.getMessage(code);
        return new ErrorResponse(code, message);
    }
}
