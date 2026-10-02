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
     * Verifies that the CSVRecord constructor stores its positional metadata
     * (record number, character position and byte position) as-is, even when
     * the positions are negative, and that size() reflects the number of values.
     */
    @Test(timeout = 4000)
    public void positionAccessorsReturnConstructorValues() throws Throwable {
        final String csvInput = "nJ=ULPJYC0~D|7x|2WT";
        final long recordNumber = 0L;
        final long characterPosition = -1060L;
        final long bytePosition = -1060L;

        CSVParser parser = CSVParser.parse(csvInput, CSVFormat.MYSQL);
        String[] values = new String[2];

        CSVRecord record = new CSVRecord(
                parser, values, csvInput, recordNumber, characterPosition, bytePosition);

        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(2, record.size());
    }
}
