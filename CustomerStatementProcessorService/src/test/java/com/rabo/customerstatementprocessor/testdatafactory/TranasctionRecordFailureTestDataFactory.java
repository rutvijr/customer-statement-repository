package com.rabo.customerstatementprocessor.testdatafactory;

import java.util.Arrays;
import java.util.List;

import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;

public class TranasctionRecordFailureTestDataFactory
{
    public static List<TransactionRecordFailure> getSampleList()
    {
        TransactionRecordFailure failure = new TransactionRecordFailure("R1", "D1");
        List<TransactionRecordFailure> failures = Arrays.asList(failure);
        return failures;
    }
}
