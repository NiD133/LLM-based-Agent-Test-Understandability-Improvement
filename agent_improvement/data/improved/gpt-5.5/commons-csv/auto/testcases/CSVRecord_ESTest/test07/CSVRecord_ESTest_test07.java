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
public class CSVRecord_ESTest_test07 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        String repeatedHeaderAndValue = "*;Ax}g<";
        String[] duplicateHeaders = new String[3];
        duplicateHeaders[0] = repeatedHeaderAndValue;
        duplicateHeaders[1] = repeatedHeaderAndValue;
        duplicateHeaders[2] = repeatedHeaderAndValue;

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        formatBuilder.setHeader(duplicateHeaders);
        CSVFormat formatWithDuplicateHeaders = formatBuilder.get();

        CSVParser parser = CSVParser.parse(repeatedHeaderAndValue, formatWithDuplicateHeaders);
        CSVRecord parsedRecord = parser.nextRecord();
        boolean nullHeaderIsSet = parsedRecord.isSet((String) null);

        assertEquals(0L, parsedRecord.getBytePosition());
        assertEquals(0L, parsedRecord.getCharacterPosition());
        assertFalse(nullHeaderIsSet);
        assertEquals(1L, parsedRecord.getRecordNumber());
        assertTrue(parsedRecord.isConsistent());
    }
}
