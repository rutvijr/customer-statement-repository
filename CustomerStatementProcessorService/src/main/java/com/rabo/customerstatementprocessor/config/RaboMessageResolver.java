package com.rabo.customerstatementprocessor.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * This class is used for resolving app-specific code into an
 * message by looking up against the message source,
 * which is the messages properties file.
 * 
 * It also takes care of merging any given context values with
 * any placeholders in the message to make it a complete message.
 * 
 * @author rutvijr@gmail.com
 */
@Component
public class RaboMessageResolver
{
    private static final String                   DEFAULT_MSG = "Message missing for the code : ";

    private ReloadableResourceBundleMessageSource messageSource;

    public RaboMessageResolver()
    {
        messageSource = new ReloadableResourceBundleMessageSource();
        // file name of the file containing the messages.
        messageSource.setBasename("messages");
    }

    /**
     * It returns the message associated to the given code by getting
     * the message from the message source.
     * 
     * It also takes care of merging any given context values with
     * any placeholders in the message to make it a complete message.
     * 
     * @param code app specific code.
     * @param contextParams values that need to be merged to the message.
     *        It is optional and when needed, >=1 parameters can be passed.
     * 
     * @return Returns the resolved error message for the given error code.
     */
    public String getMessage(String code, Object... contextParams)
    {
        return messageSource.getMessage(code, contextParams, DEFAULT_MSG + code, LocaleContextHolder.getLocale());
    }
}

