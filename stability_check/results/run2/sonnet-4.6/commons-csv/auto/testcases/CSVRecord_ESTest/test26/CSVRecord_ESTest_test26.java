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

    @Test(timeout = 4000)
    public void test_getBytePosition_returnsConstructorBytePositionValue() throws Throwable {
        // Arrange: build a CSVRecord with explicit byte and character positions
        final String csvInput = "nJ=ULPJYC0~D|7x|2WT";
        final long recordNumber = 0L;
        final long characterPosition = -1060L;
        final long bytePosition = -1060L;

        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse(csvInput, mysqlFormat);
        String[] twoElementValues = new String[2];
        CSVRecord record = new CSVRecord(parser, twoElementValues, csvInput, recordNumber, characterPosition, bytePosition);

        // Act: retrieve the byte position stored in the record
        long actualBytePosition = record.getBytePosition();

        // Assert: byte position, character position, record number, and size all match what was supplied
        assertEquals(bytePosition, actualBytePosition);
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(twoElementValues.length, record.size());
    }
}
