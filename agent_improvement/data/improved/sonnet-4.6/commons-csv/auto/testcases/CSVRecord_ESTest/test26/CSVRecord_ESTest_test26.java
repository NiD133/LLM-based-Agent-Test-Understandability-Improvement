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
    public void test26() throws Throwable {
        // Build a CSVRecord with explicit byte and character positions set to -1060
        CSVFormat mysqlFormat = CSVFormat.MYSQL;
        CSVParser parser = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", mysqlFormat);

        String[] recordValues = new String[2];
        long recordNumber = 0L;
        long expectedPosition = -1060L;

        CSVRecord record = new CSVRecord(parser, recordValues, "nJ=ULPJYC0~D|7x|2WT",
                recordNumber, expectedPosition, expectedPosition);

        // Verify that getBytePosition returns the value passed at construction time
        long bytePosition = record.getBytePosition();

        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(expectedPosition, bytePosition);
        assertEquals(expectedPosition, record.getCharacterPosition());
        assertEquals(recordValues.length, record.size());
    }
}
