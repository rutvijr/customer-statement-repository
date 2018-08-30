package com.rabo.customerstatementprocessor.exception;

/**
 * This class contains the app-specific error code and also takes in the usual
 * exception message and cause (if any).
 * 
 * It extends RuntimeException and is therefore an unchecked exception.
 * See the Spring framework's reference doc for the rationale behind this
 * approach - not all exceptions can be handled and have alternate flows.
 * Unchecked exceptions also allow for concise Functional Programming code.
 * 
 * Even though its subclasses usually carry no extra info currently, having a
 * separate exceptions allows code handling to catch and handle a particular
 * exception specifically and not have to catch a base exception and then check
 * if the exception is a specific exception of interest.
 * 
 * Example: A Insurance service that checks if a Insurance Verification task is
 * already present or not and does business logic based on that. For the
 * insurance service, a Resource not found exception from the Task service isn't
 * something to be propagated; it's something to check specifically for and do
 * alternate business logic.
 * 
 * @author rutvijr@gmail.com
 *
 */
public class RaboBaseException extends RuntimeException
{
    private static final long serialVersionUID = -3553649366746951067L;

    private final String      code;

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
    public RaboBaseException(String code, String message)
    {
        super(message);
        this.code = code;
    }

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
    public RaboBaseException(String code, String message, Throwable cause)
    {
        super(message, cause);
        this.code = code;
    }

    /**
     * It returns the app-specific code.
     *
     * @return the app-specific code.
     */
    public String getCode()
    {
        return code;
    }
}
