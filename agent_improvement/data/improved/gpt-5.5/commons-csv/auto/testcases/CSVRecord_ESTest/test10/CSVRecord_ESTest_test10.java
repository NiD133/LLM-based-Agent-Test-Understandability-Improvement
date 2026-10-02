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
public class CSVRecord_ESTest_test10 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final String repeatedHeaderAndValue = "*;Ax}g<";
        final long storedPosition = -1013L;

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] recordValues = new String[2];
        recordValues[0] = repeatedHeaderAndValue;
        recordValues[1] = repeatedHeaderAndValue;

        CSVFormat.Builder formatWithDuplicateHeaders = formatBuilder.setHeader(recordValues);
        CSVFormat format = formatWithDuplicateHeaders.get();
        CSVParser parser = CSVParser.parse(repeatedHeaderAndValue, format);
        CSVRecord record = new CSVRecord(parser, recordValues, repeatedHeaderAndValue, storedPosition, storedPosition, storedPosition);

        boolean isConsistent = record.isConsistent();

        assertEquals(storedPosition, record.getBytePosition());
        assertEquals(storedPosition, record.getCharacterPosition());
        assertEquals(storedPosition, record.getRecordNumber());
        assertFalse(isConsistent);
        assertEquals(2, record.size());
    }
}
