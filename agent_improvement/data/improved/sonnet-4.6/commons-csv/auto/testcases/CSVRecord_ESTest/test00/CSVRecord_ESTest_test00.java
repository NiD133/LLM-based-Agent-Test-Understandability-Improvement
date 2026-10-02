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
public class CSVRecord_ESTest_test00 extends CSVRecord_ESTest_scaffolding {

    // The same value is used for every header column and as the single-field CSV input
    private static final String SHARED_HEADER_AND_VALUE = "*;Ax}g<";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build a format with three headers that all share the same name
        CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        String[] headers = new String[]{SHARED_HEADER_AND_VALUE, SHARED_HEADER_AND_VALUE, SHARED_HEADER_AND_VALUE};
        formatBuilder.setHeader(headers);
        CSVFormat format = formatBuilder.get();

        // Parse a CSV string that contains exactly one field matching the duplicate header name
        CSVParser parser = CSVParser.parse(SHARED_HEADER_AND_VALUE, format);
        CSVRecord record = parser.nextRecord();

        // toMap() maps each header to its value; duplicate headers collapse to the last index
        record.toMap();

        // Verify position and size metadata for the first (and only) parsed record
        assertEquals(1L, record.getRecordNumber());
        assertEquals(1,  record.size());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
    }
}
