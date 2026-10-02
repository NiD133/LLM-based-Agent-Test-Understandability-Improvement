package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test18 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that the CSVRecord constructor stores the record number,
     * character position and byte position, and exposes them through their
     * respective getters.
     */
    @Test(timeout = 4000)
    public void positionGettersReturnConstructorValues() throws Throwable {
        final long recordNumber = 3988L;
        final long characterPosition = 3988L;
        final long bytePosition = -977L;

        CSVRecord record = new CSVRecord(
                (CSVParser) null,
                (String[]) null,
                "h%l{_WoZB#FA_}`",
                recordNumber,
                characterPosition,
                bytePosition);

        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(recordNumber, record.getRecordNumber());
    }
}
