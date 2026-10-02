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
public class CSVRecord_ESTest_test11 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that a CSVRecord parsed from a plain string (no header mapping) is
     * considered consistent, starts at position 0, has exactly one value, and is
     * the first record (record number 1).
     */
    @Test(timeout = 4000)
    public void test_isConsistent_returnsTrueWhenNoHeaderMappingIsDefined() throws Throwable {
        // Parse a single-field CSV string using the default format (no header row)
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("org.apach.commons.io.input.UnsynchronizedFilterInputStream$Builder", defaultFormat);

        // Retrieve the first (and only) record from the parsed input
        CSVRecord record = parser.nextRecord();

        // isConsistent() returns true when there is no header mapping, because
        // consistency is defined as: no header map, OR header map size == record size
        boolean consistent = record.isConsistent();

        assertEquals(0L, record.getBytePosition());
        assertEquals(1, record.size());
        assertEquals(1L, record.getRecordNumber());
        assertTrue(consistent);
        assertEquals(0L, record.getCharacterPosition());
    }
}
