package com.rabo.customerstatementprocessor.exception;

/**
 * This class extends RaboBaseException and is used to indicate to the
 * caller that a business exception has occurred.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
public class RaboBusinessException extends RaboBaseException
{
    private static final long serialVersionUID = -3553649366746951067L;

    /**
     * It constructs a new instance of this exception with the specified code
     * and exception message.
     *
     * @param code the app-specific code.
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method). (A <tt>null</tt> value is
     *        permitted, and indicates that the cause is nonexistent or
     *        unknown.)
     */
    public RaboBusinessException(String code, String message)
    {
        super(code, message);
    }
}