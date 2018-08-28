package com.rabo.customerstatementprocessor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * This class contains the details (reference and description)
 * of a failed transaction.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRecordFailure
{
    private final String transactionReference;

    private final String transactionDescription;

    public TransactionRecordFailure(String transactionReference, String transactionDescription)
    {
        this.transactionReference = transactionReference;
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionReference()
    {
        return transactionReference;
    }

    public String getTransactionDescription()
    {
        return transactionDescription;
    }
}
