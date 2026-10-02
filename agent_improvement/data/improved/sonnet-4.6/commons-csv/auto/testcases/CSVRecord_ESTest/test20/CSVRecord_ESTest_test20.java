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
public class CSVRecord_ESTest_test20 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that getRecordNumber() returns the record number supplied at construction,
     * and that getCharacterPosition() and getBytePosition() return their construction-time values.
     * The record is created with no parser (null), two blank values, an empty comment,
     * record number 1105, character position 0, and byte position 0.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Two-element values array; both slots are null (default for String[])
        String[] twoElementValues = new String[2];

        CSVRecord record = new CSVRecord(
                (CSVParser) null, // no associated parser
                twoElementValues,
                "",    // empty comment
                1105L, // record number
                0L,    // character position
                0L     // byte position
        );

        long recordNumber = record.getRecordNumber();

        // Character and byte positions were set to 0 at construction
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());

        // The record number must match the value supplied at construction
        assertEquals(1105L, recordNumber);

        // The size must equal the length of the values array passed at construction
        assertEquals(2, record.size());
    }
}
