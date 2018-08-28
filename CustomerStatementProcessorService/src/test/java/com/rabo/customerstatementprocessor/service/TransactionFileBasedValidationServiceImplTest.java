package com.rabo.customerstatementprocessor.service;

import static com.rabo.customerstatementprocessor.constant.MessageCodeConstants.UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.rabo.customerstatementprocessor.config.RaboMessageResolver;
import com.rabo.customerstatementprocessor.exception.RaboBadRequestException;
import com.rabo.customerstatementprocessor.model.TransactionRecord;
import com.rabo.customerstatementprocessor.model.TransactionRecordFailure;
import com.rabo.customerstatementprocessor.service.file.CsvFileService;
import com.rabo.customerstatementprocessor.service.file.XmlFileService;
import com.rabo.customerstatementprocessor.service.validation.TransactionValidationService;
import com.rabo.customerstatementprocessor.service.validation.impl.TransactionIOBasedValidationServiceImple;
import com.rabo.customerstatementprocessor.testdatafactory.TranasctionRecordFailureTestDataFactory;

public class TransactionFileBasedValidationServiceImplTest
{
    @Rule
    public MockitoRule                                  rule = MockitoJUnit.rule();

    @Mock
    private RaboMessageResolver                         messageResolver;

    @Mock
    private TransactionValidationService                transactionValidationService;

    @Mock
    private CsvFileService<TransactionRecord>           csvFileIterationService;

    @Mock
    private XmlFileService<TransactionRecord>           xmlFileIterationService;

    @Captor
    private ArgumentCaptor<Iterator<TransactionRecord>> transactionRecordsCaptor;

    private InputStream                                 mockedInputStream;

    private Iterator<TransactionRecord>                 mockedIterator;

    @InjectMocks
    private TransactionIOBasedValidationServiceImple    service;

    @Before
    public void setup()
    {
        mockedInputStream = Mockito.mock(InputStream.class);
    }

    @Test
    public void testCsvSuccess()
    {
        List<TransactionRecordFailure> expectedOutput = TranasctionRecordFailureTestDataFactory.getSampleList();
        when(transactionValidationService.validate(ArgumentMatchers.<Iterator<TransactionRecord>>any())).thenReturn(expectedOutput);
        when(csvFileIterationService.getIterator(mockedInputStream,
                                                 TransactionRecord.class)).thenReturn(mockedIterator);
        List<TransactionRecordFailure> actualOutput = service.validate(mockedInputStream, "csv");
        assertThat(actualOutput).isEqualTo(expectedOutput);
        verify(transactionValidationService).validate(transactionRecordsCaptor.capture());
        Iterator<TransactionRecord> actualIterator = transactionRecordsCaptor.getValue();
        assertThat(actualIterator).isEqualTo(mockedIterator);
    }

    @Test
    public void testXmlSuccess()
    {
        List<TransactionRecordFailure> expectedOutput = TranasctionRecordFailureTestDataFactory.getSampleList();
        when(transactionValidationService.validate(ArgumentMatchers.<Iterator<TransactionRecord>>any())).thenReturn(expectedOutput);
        when(xmlFileIterationService.getIterator(mockedInputStream,
                                                 TransactionRecord.class)).thenReturn(mockedIterator);
        List<TransactionRecordFailure> actualOutput = service.validate(mockedInputStream, "xml");
        assertThat(actualOutput).isEqualTo(expectedOutput);
        verify(transactionValidationService).validate(transactionRecordsCaptor.capture());
        Iterator<TransactionRecord> actualIterator = transactionRecordsCaptor.getValue();
        assertThat(actualIterator).isEqualTo(mockedIterator);
    }

    @Test(expected = RaboBadRequestException.class)
    public void testInvalidFileFormat()
    {
        String fileFormat = "asfa3r";
        when(messageResolver.getMessage(UNSUPPORTED_FILE_FORMAT_EXCEPTION_CODE,
                                        fileFormat)).thenReturn("Unsupported file format : " + fileFormat);
        service.validate(mockedInputStream, fileFormat);
    }
}
