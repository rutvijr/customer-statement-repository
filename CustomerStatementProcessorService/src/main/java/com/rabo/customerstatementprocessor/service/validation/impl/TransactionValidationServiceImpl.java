package com.rabo.customerstatementprocessor.service.validation.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rabo.customerstatementprocessor.model.TransactionRecord;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.validation.TransactionValidationService;

/**
 * This class has methods that can validate a sequence of transaction
 * records. The transaction records might be in-memory (such as a Collection)
 * or could be from secondary storage.
 * 
 * @author rutvij.ravii@cardinalhealth.com
 *
 */
@Service
public class TransactionValidationServiceImpl implements TransactionValidationService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionValidationServiceImpl.class);

    /**
     * It validates the sequence of Transaction records that is obtained using
     * the given Iterator.
     *
     * @param transactions the transaction record Iterator
     * @return the list of transaction record failures
     */
    @Override
    public List<TransactionRecordFailure> validate(Iterator<TransactionRecord> transactionRecords)
    {
        List<TransactionRecordFailure> failures = new ArrayList<>();
        Set<String> referencesSoFar = new HashSet<>();

        // Vacuous truth is applied here as in JDK 8's Stream#allmatch()
        // That is, if no records in the input, no failures in the output.
        while (transactionRecords.hasNext())
        {
            TransactionRecord record = transactionRecords.next();
            String reference = record.getReference();
            LOGGER.debug("Validating Transaction record with reference '{}'", reference);
            boolean isUniqueReference = referencesSoFar.add(reference);
            LOGGER.debug("Uniqueness is {} for Transaction record with reference '{}'", isUniqueReference, reference);
            // short circuit - a transaction that fails even one of the
            // validations is a failed one.
            if (!isUniqueReference || !isEndBalanceValid(record))
            {
                LOGGER.debug("The Transaction record with reference '{}' is not valid.", reference);
                failures.add(new TransactionRecordFailure(reference, record.getDescription()));
            }
        }

        return failures;
    }

    /**
     * It checks if the given transaction record's end balance is valid.
     * A transaction's endBalance must be equal to (startBalance + mutation),
     * where the mutation can be +ve, 0, or -ve number.
     *
     * @param record
     * @return
     */
    private boolean isEndBalanceValid(TransactionRecord record)
    {
        BigDecimal startBalance = record.getStartBalance();
        BigDecimal mutation = record.getMutation();
        BigDecimal endBalance = record.getEndBalance();

        BigDecimal expectedEndBalance = startBalance.add(mutation);
        boolean isEndBalanceValid = expectedEndBalance.compareTo(endBalance) == 0;
        String reference = record.getReference();
        LOGGER.debug("End Balance validity is {} for Transaction record with reference '{}'",
                     isEndBalanceValid,
                     reference);
        return isEndBalanceValid;
    }
}
