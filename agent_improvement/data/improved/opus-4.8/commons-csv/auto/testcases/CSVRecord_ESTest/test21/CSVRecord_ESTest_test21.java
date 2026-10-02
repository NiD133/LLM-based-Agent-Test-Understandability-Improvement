package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test21 extends CSVRecord_ESTest_scaffolding {

    /**
     * Builds a CSVRecord directly through its constructor (with no originating
     * parser) and verifies that each positional value passed to the constructor
     * is exposed unchanged by the corresponding getter.
     */
    @Test(timeout = 4000)
    public void recordExposesConstructorArgumentsAndHasNoParser() throws Throwable {
        CSVParser noParser = null;
        String[] twoValues = new String[2];
        String comment = "org.apache.commons.io.output.UncheckedFilterWriter";
        long recordNumber = 0L;
        long characterPosition = -3204L;
        long bytePosition = -416L;

        CSVRecord record = new CSVRecord(
                noParser, twoValues, comment, recordNumber, characterPosition, bytePosition);

        assertNull("Record created without a parser should report none", record.getParser());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(2, record.size());
        assertEquals(recordNumber, record.getRecordNumber());
    }
}
