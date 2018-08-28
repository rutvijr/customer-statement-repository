package com.rabo.customerstatementprocessor.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * It contains the customer statement validation response - the list of
 * transactions (with their reference and description) that failed validation.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementValidationResponse
{
    private final List<TransactionRecordFailure> customerStatementValidationResponse;

    public StatementValidationResponse(List<TransactionRecordFailure> customerStatementValidationResponse)
    {
        this.customerStatementValidationResponse = customerStatementValidationResponse;
    }

    public List<TransactionRecordFailure> getCustomerStatementValidationResponse()
    {
        return customerStatementValidationResponse;
    }
}
