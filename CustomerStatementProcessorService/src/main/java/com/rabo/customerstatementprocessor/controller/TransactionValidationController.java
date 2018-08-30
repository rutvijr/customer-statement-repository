package com.rabo.customerstatementprocessor.controller;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;
import com.rabo.customerstatementprocessor.model.ErrorResponse;
import com.rabo.customerstatementprocessor.model.StatementValidationResponse;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.validation.TransactionIOBasedValidationService;
import com.rabo.customerstatementprocessor.util.MimeTypeUtils;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class is the controller that has the REST API endpoints for validating
 * customer statement records.
 * 
 * @author rutvijr@gmail.com
 *
 */
@RestController
public class TransactionValidationController
{
    private final RaboMessageResolver                 messageResolver;

    private final TransactionIOBasedValidationService ioBasedValidationService;

    public TransactionValidationController(RaboMessageResolver messageResolver,
                                           TransactionIOBasedValidationService ioBasedValidationService)
    {
        this.messageResolver = messageResolver;
        this.ioBasedValidationService = ioBasedValidationService;
    }

    /**
     * It validates the customer transaction statement records in the given
     * MultipartFile.
     * 
     * Currently supported file types are CSV and XML.
     *
     * @param file
     * @return
     */
    @ApiResponses({@ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
                   @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)})
    @PostMapping("/validate")
    public StatementValidationResponse validate(@RequestPart MultipartFile file)
    {
        try (InputStream inputStream = file.getInputStream())
        {
            String fileType = MimeTypeUtils.getSubTypeOf(file.getContentType());
            List<TransactionRecordFailure> failures = ioBasedValidationService.validate(inputStream, fileType);
            return new StatementValidationResponse(failures);
        }
        catch (IOException exception)
        {
            String message = messageResolver.getMessage(IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE);
            throw new RaboSystemException(IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE, message, exception);
        }
    }
}
