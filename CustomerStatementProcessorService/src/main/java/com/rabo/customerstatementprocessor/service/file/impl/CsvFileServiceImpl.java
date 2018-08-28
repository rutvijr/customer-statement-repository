package com.rabo.customerstatementprocessor.service.file.impl;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.IO_EXCEPTION_WHEN_CREATING_CSV_ITERATOR_CODE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;
import com.rabo.customerstatementprocessor.service.file.CsvFileService;

/**
 * This class contains useful methods that return abstractions (such as an
 * Iterator) for working with CSV files.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@Service
public class CsvFileServiceImpl<T> implements CsvFileService<T>
{
    private final RaboMessageResolver messageResolver;

    public CsvFileServiceImpl(RaboMessageResolver messageResolver)
    {
        this.messageResolver = messageResolver;
    }

    /**
     * It returns an Iterator (of the given target domain model class) for
     * traversing the underlying inputstream that represents CSV data.
     * 
     * Implementation Note:
     * It takes the CSV schema from the target class's Jackson annotations.
     * 
     * @param inputStream the inputstream that represents CSV data.
     * @param targetClass the domain model class.
     */
    @Override
    public Iterator<T> getIterator(InputStream inputStream, Class<T> targetClass)
    {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        try
        {
            return mapper.readerFor(targetClass).with(schema).readValues(inputStream);
        }
        catch (IOException exception)
        {
            String code = IO_EXCEPTION_WHEN_CREATING_CSV_ITERATOR_CODE;
            String message = messageResolver.getMessage(code);
            throw new RaboSystemException(code, message, exception);
        }
    }
}
