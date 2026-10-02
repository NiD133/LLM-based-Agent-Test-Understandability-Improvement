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
public class CSVRecord_ESTest_test26 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that CSVRecord correctly stores and returns the byte position,
     * character position, record number, and size supplied at construction time.
     * Uses out-of-range (negative) position values to ensure they are stored verbatim.
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Arrange
        final String csvContent = "nJ=ULPJYC0~D|7x|2WT";
        final String comment     = csvContent;
        final long   recordNumber       = 0L;
        final long   characterPosition  = -1060L;
        final long   bytePosition       = -1060L;
        final int    expectedValueCount  = 2;

        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser      = CSVParser.parse(csvContent, mysqlFormat);
        String[]  values      = new String[expectedValueCount];

        CSVRecord record = new CSVRecord(parser, values, comment, recordNumber, characterPosition, bytePosition);

        // Act
        long actualBytePosition = record.getBytePosition();

        // Assert – byte/character positions and record metadata are stored as supplied
        assertEquals(bytePosition,       actualBytePosition);
        assertEquals(characterPosition,  record.getCharacterPosition());
        assertEquals(recordNumber,       record.getRecordNumber());
        assertEquals(expectedValueCount, record.size());
    }
}
