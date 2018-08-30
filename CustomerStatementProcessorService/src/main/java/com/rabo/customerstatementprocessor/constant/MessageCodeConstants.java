package com.rabo.customerstatementprocessor.constant;

/**
 * This class contains the app-specific message codes (primarily the error
 * message codes) that are used to lookup the messages from a message source.
 * 
 * @author rutvijr@gmail.com
 *
 */
public class MessageCodeConstants
{
    private MessageCodeConstants()
    {
        // Hiding the public constructor
    }

    public static final String UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE                          = "CSP1001";

    public static final String INTERNAL_SERVER_ERROR_CODE                                      = "CSP9001";

    public static final String IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE                     = "CSP9002";

    public static final String IO_EXCEPTION_WHEN_CREATING_CSV_ITERATOR_CODE                    = "CSP9003";

    public static final String IO_EXCEPTION_WHEN_CREATING_XML_ITERATOR_CODE                    = "CSP9004";

    public static final String XML_STREAM_EXCEPTION_WHEN_TRYING_TO_CHECK_FOR_NEXT_ELEMENT_CODE = "CSP9005";

    public static final String XML_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE       = "CSP9006";

    public static final String IO_STREAM_EXCEPTION_WHEN_TRYING_TO_GET_NEXT_ELEMENT_CODE        = "CSP9007";
}
