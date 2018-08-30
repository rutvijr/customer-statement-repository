package com.rabo.customerstatementprocessor.service.file;

import java.io.InputStream;
import java.util.Iterator;

/**
 * This class contains useful methods that return abstractions (such as an
 * Iterator) for working with XML files.
 * 
 * @author rutvijr@gmail.com
 *
 */
public interface XmlFileService<T>
{
    /**
     * It returns an Iterator (of the given target domain model class) for
     * traversing the underlying inputstream that represents XML data.
     * 
     * PreCondition: The XML must consist of a root element that encapsulates an
     * array of XML elements, each of which are marshallable to the targetClass.
     * 
     * Implementation Note:
     * It takes the XML element structure from the target class's Jackson
     * annotations.
     * 
     * @param inputStream the inputstream that represents XML data.
     * @param targetClass the domain model class.
     */
    Iterator<T> getIterator(InputStream inputStream, Class<T> targetClass);
}
