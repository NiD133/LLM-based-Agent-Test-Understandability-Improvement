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
public class CSVRecord_ESTest_test06 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final String duplicateHeaderName = "*;Ax}g<";

        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] duplicateHeaders = new String[2];
        duplicateHeaders[0] = duplicateHeaderName;
        duplicateHeaders[1] = duplicateHeaderName;
        formatBuilder.setHeader(duplicateHeaders);

        CSVFormat format = formatBuilder.get();
        CSVParser parser = CSVParser.parse(duplicateHeaderName, format);
        CSVRecord record = parser.nextRecord();

        boolean duplicateHeaderHasValue = record.isSet(duplicateHeaderName);

        assertEquals(1L, record.getRecordNumber());
        assertFalse(duplicateHeaderHasValue);
        assertEquals(1, record.size());
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
    }
}
