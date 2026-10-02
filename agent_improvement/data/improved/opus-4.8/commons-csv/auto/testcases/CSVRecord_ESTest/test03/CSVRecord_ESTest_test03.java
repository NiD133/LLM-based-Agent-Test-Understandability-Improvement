package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test03 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that a CSVRecord created with two values reports column index 1 as set,
     * and that the positional metadata passed to the constructor is returned unchanged.
     */
    @Test(timeout = 4000)
    public void isSet_returnsTrueForExistingColumn_andExposesConstructorPositions() throws Throwable {
        // Record with two value slots (indices 0 and 1) and explicit position metadata.
        String[] values = new String[2];
        long recordNumber = 1105L;
        long characterPosition = 1491L;
        long bytePosition = 2147483639L;
        CSVRecord record = new CSVRecord(
                (CSVParser) null, values, "", recordNumber, characterPosition, bytePosition);

        // Index 1 is within the value array, so it is considered "set".
        boolean isColumnOneSet = record.isSet(1);
        assertTrue(isColumnOneSet);

        // The constructor's positional metadata must be returned verbatim.
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(bytePosition, record.getBytePosition());
    }
}
