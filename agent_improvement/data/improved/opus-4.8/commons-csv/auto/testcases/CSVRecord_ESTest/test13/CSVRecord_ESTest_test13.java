package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test13 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-field CSV line and verifies the metadata of the resulting
     * record: it carries no comment and reports the expected position, record
     * number and size.
     */
    @Test(timeout = 4000)
    public void firstRecordOfSingleFieldInputHasExpectedMetadata() throws Throwable {
        String singleFieldInput = "*;Ax}g<";

        CSVParser parser = CSVParser.parse(singleFieldInput, CSVFormat.DEFAULT);
        CSVRecord firstRecord = parser.nextRecord();

        assertFalse("record should have no comment", firstRecord.hasComment());
        assertEquals("record holds one field", 1, firstRecord.size());
        assertEquals("first record is numbered 1", 1L, firstRecord.getRecordNumber());
        assertEquals("record starts at character position 0", 0L, firstRecord.getCharacterPosition());
        assertEquals("record starts at byte position 0", 0L, firstRecord.getBytePosition());
    }
}
