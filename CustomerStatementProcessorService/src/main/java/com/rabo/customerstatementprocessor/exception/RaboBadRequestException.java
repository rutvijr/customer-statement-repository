package com.rabo.customerstatementprocessor.exception;

/**
 * This class extends RaboBusinessException and is used to indicate to the
 * caller that a bad request exception has occurred.
 * 
 * @author rutvijr@gmail.com
 *
 */
public class RaboBadRequestException extends RaboBusinessException
{
    private static final long serialVersionUID = 1306503345457945871L;

    public RaboBadRequestException(String code, String message)
    {
        super(code, message);
    }
}
