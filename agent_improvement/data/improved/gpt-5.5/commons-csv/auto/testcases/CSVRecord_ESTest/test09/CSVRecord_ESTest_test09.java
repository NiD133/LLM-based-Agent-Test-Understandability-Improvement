package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test09 extends CSVRecord_ESTest_scaffolding {

    private static final String HEADER_AND_RECORD_VALUE = "*;Ax}g<";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] duplicateHeaders = new String[3];
        duplicateHeaders[0] = HEADER_AND_RECORD_VALUE;
        duplicateHeaders[1] = HEADER_AND_RECORD_VALUE;
        duplicateHeaders[2] = HEADER_AND_RECORD_VALUE;

        CSVFormat.Builder configuredBuilder = formatBuilder.setHeader(duplicateHeaders);
        CSVFormat format = configuredBuilder.get();
        CSVParser parser = CSVParser.parse(HEADER_AND_RECORD_VALUE, format);
        CSVRecord record = parser.nextRecord();

        boolean isConsistent = record.isConsistent();

        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
        assertTrue(isConsistent);
    }
}
