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
public class CSVRecord_ESTest_test25 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that the positional metadata passed into the {@link CSVRecord}
     * constructor (record number, character position and byte position) is
     * stored verbatim and returned by the corresponding getters, alongside the
     * record's value count.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        final String csvLine = "nJ=ULPJYC0~D|7x|2WT";
        final long expectedRecordNumber = 1100L;
        final long expectedCharacterPosition = -1L;
        final long expectedBytePosition = 2147483639L;

        CSVParser parser = CSVParser.parse(csvLine, CSVFormat.MYSQL);

        // A two-element values array; the comment argument reuses the csvLine text.
        String[] values = new String[2];
        CSVRecord record = new CSVRecord(parser, values, csvLine,
                expectedRecordNumber, expectedCharacterPosition, expectedBytePosition);

        record.getComment();
        assertEquals("Record should expose its two values", 2, record.size());
        assertEquals(expectedCharacterPosition, record.getCharacterPosition());
        assertEquals(expectedBytePosition, record.getBytePosition());
        assertEquals(expectedRecordNumber, record.getRecordNumber());
    }
}
