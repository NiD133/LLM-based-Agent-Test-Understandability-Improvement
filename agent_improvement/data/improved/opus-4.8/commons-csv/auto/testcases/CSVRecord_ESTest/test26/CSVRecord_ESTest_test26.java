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
     * Verifies that a CSVRecord exposes the position metadata supplied to its
     * constructor (record number, character position and byte position) and that
     * its size reflects the number of values it was given.
     */
    @Test(timeout = 4000)
    public void constructorStoresPositionsAndSize() throws Throwable {
        final long recordNumber = 0L;
        final long characterPosition = -1060L;
        final long bytePosition = -1060L;
        final String[] values = new String[2];

        CSVParser parser = CSVParser.parse("nJ=ULPJYC0~D|7x|2WT", CSVFormat.MYSQL);
        CSVRecord record = new CSVRecord(parser, values, "nJ=ULPJYC0~D|7x|2WT",
                recordNumber, characterPosition, bytePosition);

        assertEquals(recordNumber, record.getRecordNumber());
        assertEquals(bytePosition, record.getBytePosition());
        assertEquals(characterPosition, record.getCharacterPosition());
        assertEquals(values.length, record.size());
    }
}
