package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test08 extends CSVRecord_ESTest_scaffolding {

    /**
     * A record created without a parser has no header mapping, so isSet(String)
     * must return false for any column name. This verifies that lookup and that
     * the record's positional metadata is preserved as passed to the constructor.
     */
    @Test(timeout = 4000)
    public void isSetByName_returnsFalse_whenRecordHasNoHeaderMapping() throws Throwable {
        String[] values = new String[2];
        long recordNumber = 1105L;
        long characterPosition = 0L;
        long bytePosition = 0L;

        CSVRecord record = new CSVRecord(
                (CSVParser) null, values, "", recordNumber, characterPosition, bytePosition);

        boolean isSet = record.isSet("");

        assertFalse("no header mapping exists, so no column name is set", isSet);
        assertEquals(1105L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
        assertEquals(2, record.size());
    }
}
