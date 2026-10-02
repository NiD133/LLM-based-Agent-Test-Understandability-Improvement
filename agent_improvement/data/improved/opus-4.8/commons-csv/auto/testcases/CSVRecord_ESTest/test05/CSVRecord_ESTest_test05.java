package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test05 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that {@link CSVRecord#isSet(int)} returns false for a negative
     * column index, and that the record's positional metadata is preserved as
     * supplied to the constructor.
     */
    @Test(timeout = 4000)
    public void isSet_withNegativeIndex_returnsFalseAndKeepsMetadata() throws Throwable {
        // Build a record with two (null) values and explicit positional metadata.
        String[] values = new String[2];
        long recordNumber = 1105L;
        long characterPosition = 0L;
        long bytePosition = 0L;
        CSVRecord record = new CSVRecord(
                (CSVParser) null, values, "", recordNumber, characterPosition, bytePosition);

        // A negative index can never reference an existing column.
        boolean negativeIndexIsSet = record.isSet(-2522);

        assertFalse(negativeIndexIsSet);
        assertEquals(2, record.size());
        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(bytePosition, record.getBytePosition());
    }
}
