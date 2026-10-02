package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test23 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-field input with the default format and verifies that the
     * first record reports starting positions of zero and renders the expected
     * {@code toString()} representation.
     */
    @Test(timeout = 4000)
    public void parsesFirstRecordWithZeroStartPositionsAndExpectedToString() throws Throwable {
        final String input = "*;Ax}g<";

        CSVParser parser = CSVParser.parse(input, CSVFormat.DEFAULT);
        CSVRecord firstRecord = parser.nextRecord();

        assertEquals(0L, firstRecord.getBytePosition());
        assertEquals(0L, firstRecord.getCharacterPosition());
        assertEquals(
                "CSVRecord [comment='null', recordNumber=1, values=[*;Ax}g<]]",
                firstRecord.toString());
    }
}
