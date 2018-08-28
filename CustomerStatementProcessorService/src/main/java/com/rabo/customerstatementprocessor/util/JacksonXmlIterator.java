package com.rabo.customerstatementprocessor.util;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.IO_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE;
import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.XML_STREAM_EXCEPTION_WHEN_TRYING_TO_CHECK_FOR_NEXT_ELEMENT_CODE;
import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.XML_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;

/**
 * It's an adapter between the Jackson incremental reading code (using StaX's
 * XMLStreamReader and Jackson's XmlMapper) and java.util.Iterator
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
public class JacksonXmlIterator<E> implements Iterator<E>
{
    private final XMLStreamReader     streamReader;
    private final XmlMapper           mapper;
    private final Class<E>            targetClass;
    private final RaboMessageResolver messageResolver;

    public JacksonXmlIterator(XMLStreamReader streamReader,
                              XmlMapper mapper,
                              Class<E> targetClass,
                              RaboMessageResolver messageResolver)
    {
        this.streamReader = streamReader;
        this.mapper = mapper;
        this.targetClass = targetClass;
        this.messageResolver = messageResolver;
    }

    /**
     * @see java.util.Iterator#hasNext()
     * 
     */
    @Override
    public boolean hasNext()
    {
        try
        {
            return streamReader.hasNext() && streamReader.getEventType() != XMLStreamReader.END_ELEMENT;
        }
        catch (XMLStreamException exception)
        {
            String code = XML_STREAM_EXCEPTION_WHEN_TRYING_TO_CHECK_FOR_NEXT_ELEMENT_CODE;
            String message = messageResolver.getMessage(code);
            throw new RaboSystemException(code, message, exception);
        }
    }

    /**
     * @see java.util.Iterator#next()
     * 
     */
    @Override
    public E next()
    {
        try
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            E element = mapper.readValue(streamReader, targetClass);
            streamReader.nextTag(); // Go to next tag.
            return element;
        }
        catch (XMLStreamException exception)
        {
            String code = XML_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE;
            String message = messageResolver.getMessage(code);
            throw new RaboSystemException(code, message, exception);
        }
        catch (IOException exception)
        {
            String code = IO_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE;
            String message = messageResolver.getMessage(code);
            throw new RaboSystemException(code, message, exception);
        }
    }
    
    /**
     * Warning: Not supported.
     * 
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("remove is not supported");
    }

    /**
     * Warning: Not supported currently.
     * 
     */
    @Override
    public void forEachRemaining(Consumer<? super E> consumer)
    {
        throw new UnsupportedOperationException("forEachRemaining is not supported currently");
    }
}
