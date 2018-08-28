package com.rabo.customerstatementprocessor.controller;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboSystemException;
import com.rabo.customerstatementprocessor.model.StatementValidationResponse;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.validation.TransactionIOBasedValidationService;
import com.rabo.customerstatementprocessor.testdatafactory.TranasctionRecordFailureTestDataFactory;

public class TransactionValidationControllerTest
{
    @Rule
    public MockitoRule                          rule = MockitoJUnit.rule();

    @Mock
    private RaboMessageResolver                 messageResolver;

    @Mock
    private TransactionIOBasedValidationService transactionFileBasedValidationService;

    private MultipartFile                       mockedMultipartFile;

    private InputStream                         mockedInputStream;

    @InjectMocks
    private TransactionValidationController     controller;

    @Before
    public void setup() throws IOException
    {
        mockedMultipartFile = Mockito.mock(MultipartFile.class);
        mockedInputStream = Mockito.mock(InputStream.class);
        when(mockedMultipartFile.getInputStream()).thenReturn(mockedInputStream);
        when(mockedMultipartFile.getContentType()).thenReturn("text/csv");
    }

    @Test
    public void testSuccess()
    {
        List<TransactionRecordFailure> failures = TranasctionRecordFailureTestDataFactory.getSampleList();
        StatementValidationResponse expectedOutput = new StatementValidationResponse(failures);
        when(transactionFileBasedValidationService.validate(mockedInputStream, "csv")).thenReturn(failures);
        StatementValidationResponse actualOutput = controller.validate(mockedMultipartFile);
        assertThat(actualOutput).isEqualToComparingFieldByFieldRecursively(expectedOutput);
        verify(transactionFileBasedValidationService).validate(mockedInputStream, "csv");
    }

    @Test(expected = RaboSystemException.class)
    public void testIOException() throws IOException
    {
        List<TransactionRecordFailure> failures = TranasctionRecordFailureTestDataFactory.getSampleList();
        when(transactionFileBasedValidationService.validate(mockedInputStream, "csv")).thenReturn(failures);
        doThrow(IOException.class).when(mockedInputStream).close();
        when(messageResolver.getMessage(IO_EXCEPTION_WHEN_READING_INPUT_STREAM_CODE)).thenReturn("IO Exception when reading input stream");
        controller.validate(mockedMultipartFile);
    }
}
