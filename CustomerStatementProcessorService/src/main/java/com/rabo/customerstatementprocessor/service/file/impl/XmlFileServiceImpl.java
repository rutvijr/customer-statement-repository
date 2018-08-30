package com.rabo.customerstatementprocessor.service.file.impl;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.IO_EXCEPTION_WHEN_CREATING_XML_ITERATOR_CODE;

import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;
import com.rabo.customerstatementprocessor.service.file.XmlFileService;
import com.rabo.customerstatementprocessor.util.JacksonXmlIterator;

/**
 * This class contains useful methods that return abstractions (such as an
 * Iterator) for working with XML files.
 * 
 * @author rutvijr@gmail.com
 *
 */
@Service
public class XmlFileServiceImpl<T> implements XmlFileService<T>
{
    private final RaboMessageResolver messageResolver;

    public XmlFileServiceImpl(RaboMessageResolver messageResolver)
    {
        this.messageResolver = messageResolver;
    }

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
    @Override
    public Iterator<T> getIterator(InputStream inputStream, Class<T> targetClass)
    {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try
        {
            XMLStreamReader streamReader = factory.createXMLStreamReader(inputStream);
            XmlMapper mapper = new XmlMapper();
            streamReader.next(); // to point to <root>
            streamReader.next(); // to point to root-element under root
            return new JacksonXmlIterator<>(streamReader, mapper, targetClass, messageResolver);
        }
        catch (XMLStreamException exception)
        {
            String code = IO_EXCEPTION_WHEN_CREATING_XML_ITERATOR_CODE;
            String message = messageResolver.getMessage(code);
            throw new RaboSystemException(code, message, exception);
        }
    }
}
