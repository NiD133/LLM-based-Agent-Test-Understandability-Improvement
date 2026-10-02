package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test03 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that isSet(index) returns true for a valid index within the values array,
     * and that record metadata (recordNumber, characterPosition, bytePosition) are stored correctly.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final long recordNumber      = 1105L;
        final long characterPosition = 1491L;
        final long bytePosition      = 2147483639L;
        final String comment         = "";
        final int checkedIndex       = 1;

        // Create a record with 2 values (indices 0 and 1 are both within bounds)
        String[] twoElementValues = new String[2];
        CSVRecord record = new CSVRecord((CSVParser) null, twoElementValues, comment, recordNumber, characterPosition, bytePosition);

        // Index 1 is within the array bounds, so isSet should return true
        boolean indexIsSet = record.isSet(checkedIndex);

        assertTrue(indexIsSet);
        assertEquals(recordNumber,      record.getRecordNumber());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition,      record.getBytePosition());
    }
}
