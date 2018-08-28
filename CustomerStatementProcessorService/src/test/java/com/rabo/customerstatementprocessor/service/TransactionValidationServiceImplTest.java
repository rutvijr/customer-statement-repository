package com.rabo.customerstatementprocessor.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.rabo.customerstatementprocessor.model.TransactionRecord;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.validation.TransactionValidationService;
import com.rabo.customerstatementprocessor.service.validation.impl.TransactionValidationServiceImpl;

public class TransactionValidationServiceImplTest
{
    private TransactionValidationService service = new TransactionValidationServiceImpl();

    @Test
    public void testNoFailures()
    {
        TransactionRecord transactionRecord1 = validTransaction();
        TransactionRecord transactionRecord2 =
                new TransactionRecord("R2",
                                      "A2",
                                      "",
                                      BigDecimal.valueOf(12.82),
                                      BigDecimal.valueOf(-7.23),
                                      BigDecimal.valueOf(5.59));
        List<TransactionRecord> transactionRecords = Arrays.asList(transactionRecord1, transactionRecord2);
        List<TransactionRecordFailure> failures = service.validate(transactionRecords.iterator());
        assertThat(failures).isEmpty();
    }

    @Test
    public void testFailureInReferenceUniqueness()
    {
        TransactionRecord transactionRecord2 =
                new TransactionRecord("R1",
                                      "A2",
                                      "D1",
                                      BigDecimal.valueOf(12.82),
                                      BigDecimal.valueOf(-7.23),
                                      BigDecimal.valueOf(5.59));
        TransactionRecord transactionRecord1 = validTransaction();
        List<TransactionRecord> transactionRecords = Arrays.asList(transactionRecord1, transactionRecord2);
        Object failures = service.validate(transactionRecords.iterator());

        TransactionRecordFailure failure1 =
                new TransactionRecordFailure(transactionRecord2.getReference(), transactionRecord2.getDescription());

        List<TransactionRecordFailure> expectedFailures = Arrays.asList(failure1);
        expectedFailures.forEach(expectedFailure -> assertThat(failures).isEqualToComparingFieldByFieldRecursively(expectedFailures));
    }

    @Test
    public void testFailureInEndBalance()
    {
        TransactionRecord transactionRecord1 = validTransaction();
        TransactionRecord transactionRecord2 =
                new TransactionRecord("R2",
                                      "A2",
                                      "D2",
                                      BigDecimal.valueOf(12.82),
                                      BigDecimal.valueOf(-7.23),
                                      BigDecimal.valueOf(8.2));
        List<TransactionRecord> transactionRecords = Arrays.asList(transactionRecord1, transactionRecord2);
        Object failures = service.validate(transactionRecords.iterator());

        TransactionRecordFailure failure1 =
                new TransactionRecordFailure(transactionRecord2.getReference(), transactionRecord2.getDescription());

        List<TransactionRecordFailure> expectedFailures = Arrays.asList(failure1);
        expectedFailures.forEach(expectedFailure -> assertThat(failures).isEqualToComparingFieldByFieldRecursively(expectedFailures));
    }

    @Test
    public void testFailureInReferenceUniquenessAndEndBalance()
    {
        TransactionRecord transactionRecord1 = validTransaction();
        TransactionRecord transactionRecord2 =
                new TransactionRecord("R1",
                                      "A2",
                                      "D2",
                                      BigDecimal.valueOf(12.82),
                                      BigDecimal.valueOf(-7.23),
                                      BigDecimal.valueOf(8.2));
        List<TransactionRecord> transactionRecords = Arrays.asList(transactionRecord1, transactionRecord2);
        Object failures = service.validate(transactionRecords.iterator());

        TransactionRecordFailure failure1 =
                new TransactionRecordFailure(transactionRecord2.getReference(), transactionRecord2.getDescription());

        List<TransactionRecordFailure> expectedFailures = Arrays.asList(failure1);
        expectedFailures.forEach(expectedFailure -> assertThat(failures).isEqualToComparingFieldByFieldRecursively(expectedFailures));
    }

    private TransactionRecord validTransaction()
    {
        BigDecimal startBalance = BigDecimal.valueOf(10.33);
        BigDecimal mutation = BigDecimal.valueOf(7);
        BigDecimal endBalance = BigDecimal.valueOf(17.33);
        TransactionRecord transactionRecord1 =
                new TransactionRecord("R1", "A1", "D1", startBalance, mutation, endBalance);
        return transactionRecord1;
    }
}
