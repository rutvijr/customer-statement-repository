package com.rabo.customerstatementprocessor.service.validation.impl;

import static com.rabo.customerstatementprocessor.constant.FileTypeConstants.CSV;
import static com.rabo.customerstatementprocessor.constant.FileTypeConstants.XML;
import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboBadRequestException;
import com.rabo.customerstatementprocessor.model.TransactionRecord;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.file.CsvFileService;
import com.rabo.customerstatementprocessor.service.file.XmlFileService;
import com.rabo.customerstatementprocessor.service.validation.TransactionIOBasedValidationService;
import com.rabo.customerstatementprocessor.service.validation.TransactionValidationService;

/**
 * TransactionFileBasedValidationServiceImpl.java
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@Service
public class TransactionIOBasedValidationServiceImple implements TransactionIOBasedValidationService
{
    private static final Logger                     LOGGER =
            LoggerFactory.getLogger(TransactionIOBasedValidationServiceImple.class);

    private final RaboMessageResolver               messageResolver;

    private final TransactionValidationService      transactionValidationService;

    private final CsvFileService<TransactionRecord> csvFileIterationService;

    private final XmlFileService<TransactionRecord> xmlFileIterationService;

    public TransactionIOBasedValidationServiceImple(RaboMessageResolver messageResolver,
                                                    TransactionValidationService transactionValidationService,
                                                    CsvFileService<TransactionRecord> csvFileIterationService,
                                                    XmlFileService<TransactionRecord> xmlFileIterationService)
    {
        this.messageResolver = messageResolver;
        this.transactionValidationService = transactionValidationService;
        this.csvFileIterationService = csvFileIterationService;
        this.xmlFileIterationService = xmlFileIterationService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.rabo.customerstatementprocessor.service.impl.
     * TransactionFileBasedValidationService#validate(java.io.InputStream,
     * java.lang.String)
     */
    @Override
    public List<TransactionRecordFailure> validate(InputStream inputStream, String fileFormat)
    {
        LOGGER.debug("File extension is {}", fileFormat);
        Iterator<TransactionRecord> transactionIterator;
        if (CSV.equalsIgnoreCase(fileFormat))
        {
            transactionIterator = csvFileIterationService.getIterator(inputStream, TransactionRecord.class);
        }
        else if (XML.equalsIgnoreCase(fileFormat))
        {
            transactionIterator = xmlFileIterationService.getIterator(inputStream, TransactionRecord.class);
        }
        else
        {
            String message = messageResolver.getMessage(UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE, fileFormat);
            throw new RaboBadRequestException(UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE, message);
        }
        return transactionValidationService.validate(transactionIterator);
    }
}
