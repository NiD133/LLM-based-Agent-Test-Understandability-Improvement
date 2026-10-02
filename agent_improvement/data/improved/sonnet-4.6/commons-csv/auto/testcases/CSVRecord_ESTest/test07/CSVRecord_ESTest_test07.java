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
    public void test_isSet_withNullColumnName_returnsFalse() throws Throwable {
        // Arrange: build a format with three duplicate header names
        String headerName = "*;Ax}g<";
        String[] headers = new String[] { headerName, headerName, headerName };
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(headers)
                .get();

        // Parse a single-value CSV string and retrieve the first record
        CSVParser parser = CSVParser.parse(headerName, format);
        CSVRecord record = parser.nextRecord();

        // Act: check whether a null column name is considered "set"
        boolean isNullColumnSet = record.isSet((String) null);

        // Assert: null is not a mapped column, so isSet should return false
        assertFalse(isNullColumnSet);
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(1L, record.getRecordNumber());
        assertTrue(record.isConsistent());
    }
}
