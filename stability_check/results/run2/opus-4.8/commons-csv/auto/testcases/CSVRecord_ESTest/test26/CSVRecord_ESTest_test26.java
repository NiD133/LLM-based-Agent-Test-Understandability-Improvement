package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test26 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that a CSVRecord exposes exactly the position and size values
     * that were supplied to its constructor.
     */
    @Test(timeout = 4000)
    public void positionAndSizeMatchConstructorArguments() throws Throwable {
        final String source = "nJ=ULPJYC0~D|7x|2WT";
        final long recordNumber = 0L;
        final long characterPosition = -1060L;
        final long bytePosition = -1060L;

        CSVParser parser = CSVParser.parse(source, CSVFormat.MYSQL);
        String[] values = new String[2];
        CSVRecord record = new CSVRecord(parser, values, source, recordNumber, characterPosition, bytePosition);

        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
