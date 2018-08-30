package com.rabo.customerstatementprocessor.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * This class contains a transaction's details.
 * 
 * @author rutvijr@gmail.com
 *
 */
public class TransactionRecord
{
    @JacksonXmlProperty(isAttribute = true)
    @JsonProperty("Reference")
    private String     reference;

    @JacksonXmlProperty
    @JsonProperty("AccountNumber")
    private String     accountNumber;

    @JacksonXmlProperty
    @JsonProperty("Description")
    private String     description;

    @JacksonXmlProperty
    @JsonProperty("Start Balance")
    private BigDecimal startBalance;

    @JacksonXmlProperty
    @JsonProperty("Mutation")
    private BigDecimal mutation;

    @JacksonXmlProperty
    @JsonProperty("End Balance")
    private BigDecimal endBalance;

    public TransactionRecord()
    {
        
    }

    public TransactionRecord(String reference, String description)
    {
        this.reference = reference;
        this.description = description;
    }

    public TransactionRecord(String reference,
                             String accountNumber,
                             String description,
                             BigDecimal startBalance,
                             BigDecimal mutation,
                             BigDecimal endBalance)
    {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.description = description;
        this.startBalance = startBalance;
        this.mutation = mutation;
        this.endBalance = endBalance;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public BigDecimal getStartBalance()
    {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance)
    {
        this.startBalance = startBalance;
    }

    public BigDecimal getMutation()
    {
        return mutation;
    }

    public void setMutation(BigDecimal mutation)
    {
        this.mutation = mutation;
    }

    public BigDecimal getEndBalance()
    {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance)
    {
        this.endBalance = endBalance;
    }
}
