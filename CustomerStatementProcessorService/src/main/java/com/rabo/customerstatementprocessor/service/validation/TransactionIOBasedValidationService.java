package com.rabo.customerstatementprocessor.service.validation;

import java.io.InputStream;
import java.util.List;

import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;

/**
 * This class validates transactions from a Java IO source, (such as an
 * InputStream).
 * 
 * @author rutvijr@gmail.com
 *
 */
public interface TransactionIOBasedValidationService
{
    /**
     * This class validates transactions from an InputStream that represents
     *
     * @param inputStream
     * @param fileExtension
     * @return
     */
    List<TransactionRecordFailure> validate(InputStream inputStream, String contentType);
}
