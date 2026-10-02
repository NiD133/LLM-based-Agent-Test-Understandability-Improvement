package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test20 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that the positional fields supplied to the CSVRecord constructor
     * are exposed verbatim by their corresponding getters, and that size()
     * reflects the length of the values array.
     */
    @Test(timeout = 4000)
    public void recordExposesConstructorValuesViaGetters() throws Throwable {
        // Two-element (null-filled) values array, so size() should report 2.
        String[] values = new String[2];

        long recordNumber = 1105L;
        long characterPosition = 0L;
        long bytePosition = 0L;

        CSVRecord record = new CSVRecord(
                (CSVParser) null,   // no originating parser
                values,
                "",                 // empty comment
                recordNumber,
                characterPosition,
                bytePosition);

        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(2, record.size());
    }
}
