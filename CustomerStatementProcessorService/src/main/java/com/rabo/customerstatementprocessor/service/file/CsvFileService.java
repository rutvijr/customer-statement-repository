package com.rabo.customerstatementprocessor.service.file;

import java.io.InputStream;
import java.util.Iterator;

/**
 * This interface contains useful methods that return abstractions (such as an
 * Iterator) for working with CSV files.
 * 
 * @author rutvijr@gmail.com
 *
 */
public interface CsvFileService<T>
{
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
    Iterator<T> getIterator(InputStream inputStream, Class<T> targetClass);
}
