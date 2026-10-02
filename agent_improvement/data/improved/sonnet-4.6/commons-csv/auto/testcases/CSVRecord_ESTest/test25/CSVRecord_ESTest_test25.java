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

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Parse a raw string using the MYSQL format to obtain a parser context
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", mysqlFormat);

        // Build a two-element values array and construct the record with explicit metadata
        String[] twoElementValues = new String[2];
        long recordNumber      = 1100L;
        long characterPosition = -1L;
        long bytePosition      = 2147483639L;
        String comment         = "nJ=ULPJYC0~D|7x|2WT";

        CSVRecord record = new CSVRecord(parser, twoElementValues, comment, recordNumber, characterPosition, bytePosition);

        // getComment() must be callable without throwing
        record.getComment();

        // Verify that all metadata is stored and retrieved correctly
        assertEquals(2,             record.size());
        assertEquals(-1L,           record.getCharacterPosition());
        assertEquals(2147483639L,   record.getBytePosition());
        assertEquals(1100L,         record.getRecordNumber());
    }
}
