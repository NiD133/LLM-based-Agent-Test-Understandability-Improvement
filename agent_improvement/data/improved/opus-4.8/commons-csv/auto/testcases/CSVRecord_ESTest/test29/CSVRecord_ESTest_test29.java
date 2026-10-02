package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test29 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-line, single-field CSV input and verifies the metadata
     * exposed by the resulting {@link CSVRecord}. Calling spliterator() should
     * not affect any of the record's positional metadata.
     */
    @Test(timeout = 4000)
    public void testRecordMetadataForSingleFieldInput() throws Throwable {
        // A line with no delimiters parses as one record holding a single field.
        CSVParser parser = CSVParser.parse("*;Ax}g<", CSVFormat.DEFAULT);
        CSVRecord firstRecord = parser.nextRecord();

        firstRecord.spliterator();

        assertEquals("First parsed record should be numbered 1",
                1L, firstRecord.getRecordNumber());
        assertEquals("Record starts at the beginning of the stream (character 0)",
                0L, firstRecord.getCharacterPosition());
        assertEquals("Record starts at the beginning of the stream (byte 0)",
                0L, firstRecord.getBytePosition());
        assertEquals("Input without delimiters yields exactly one field",
                1, firstRecord.size());
    }
}
