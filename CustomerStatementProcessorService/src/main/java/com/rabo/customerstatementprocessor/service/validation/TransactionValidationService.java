package com.rabo.customerstatementprocessor.service.validation;

import java.util.Iterator;
import java.util.List;

import com.rabo.customerstatementprocessor.model.TransactionRecord;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;

/**
 * This interface has methods that can validate a sequence of transaction
 * records. The transaction records might be in-memory (such as a Collection)
 * or could be from secondary storage.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
public interface TransactionValidationService
{
    /**
     * It validates the sequence of Transaction records that is obtained using
     * the given Iterator.
     *
     * @param transactions the transaction record Iterator
     * @return the list of transaction record failures
     */
    List<TransactionRecordFailure> validate(Iterator<TransactionRecord> transactions);
}
