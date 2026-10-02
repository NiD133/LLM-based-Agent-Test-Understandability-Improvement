package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test04 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that {@link CSVRecord#isSet(int)} returns false for a column index
     * that is outside the record's value range, and that the position/size
     * accessors return the values supplied to the constructor.
     */
    @Test(timeout = 4000)
    public void isSet_withIndexBeyondValues_returnsFalseAndPreservesMetadata() throws Throwable {
        // A record with two (null) values, record number 1105, and zero positions.
        String[] values = new String[2];
        long recordNumber = 1105L;
        long characterPosition = 0L;
        long bytePosition = 0L;
        CSVRecord record = new CSVRecord(
                (CSVParser) null, values, "", recordNumber, characterPosition, bytePosition);

        // Index 3174 is far beyond the two available columns, so it is not set.
        int indexBeyondValues = 3174;
        boolean isSet = record.isSet(indexBeyondValues);

        assertFalse(isSet);
        assertEquals(2, record.size());
        assertEquals(1105L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
    }
}
