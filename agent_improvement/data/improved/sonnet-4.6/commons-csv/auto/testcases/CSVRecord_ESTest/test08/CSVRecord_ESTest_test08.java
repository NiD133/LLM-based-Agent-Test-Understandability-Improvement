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
public class CSVRecord_ESTest_test08 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that isSet(String) returns false when the record has no header mapping (parser is null),
     * and that the record's positional metadata (record number, byte/character position) and size are correct.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Create a 2-element record with no parser (null), an empty comment, record number 1105,
        // character position 0, and byte position 0.
        String[] values = new String[2];
        CSVRecord record = new CSVRecord((CSVParser) null, values, "", 1105L, 0L, 0L);

        // isSet(String) requires a header mapping from the parser; with a null parser it always returns false.
        boolean columnIsSet = record.isSet("");

        assertFalse("Expected isSet(\"\") to be false because no header mapping exists (null parser)", columnIsSet);
        assertEquals("Record number should be 1105", 1105L, record.getRecordNumber());
        assertEquals("Byte position should be 0", 0L, record.getBytePosition());
        assertEquals("Character position should be 0", 0L, record.getCharacterPosition());
        assertEquals("Record size should match the values array length of 2", 2, record.size());
    }
}
