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
public class CSVRecord_ESTest_test27 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that parsing a single-field CSV string with ORACLE format produces
     * a CSVRecord with the correct record number, value count, and stream positions.
     *
     * The input string contains no commas, so ORACLE format (comma delimiter) treats
     * the entire string as one field, yielding exactly one value in the record.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        // A string with no commas is a single-field CSV row under ORACLE format
        String csvInput = "org.apache.commons.io.input.UncheckedBufferedReader$Builder";
        CSVFormat oracleFormat = CSVFormat.ORACLE;

        CSVParser parser = CSVParser.parse(csvInput, oracleFormat);
        CSVRecord firstRecord = parser.nextRecord();
        String[] values = firstRecord.values();

        // The parser assigns record number 1 to the first parsed record
        assertEquals(1L, firstRecord.getRecordNumber());
        // No commas in the input, so the whole string is a single field
        assertEquals(1, values.length);
        // Parsing starts at the beginning of the stream: byte and character positions are both 0
        assertEquals(0L, firstRecord.getBytePosition());
        assertEquals(0L, firstRecord.getCharacterPosition());
    }
}
