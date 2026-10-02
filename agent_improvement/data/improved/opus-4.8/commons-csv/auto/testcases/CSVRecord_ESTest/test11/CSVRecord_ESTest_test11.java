package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test11 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-line, single-field CSV input and verifies the metadata of
     * the resulting record: it is the first record, has one value, starts at the
     * very beginning of the stream, and is consistent (no header mapping exists).
     */
    @Test(timeout = 4000)
    public void parsesSingleFieldRecordAndReportsExpectedMetadata() throws Throwable {
        final String singleFieldLine = "org.apach.commons.io.input.UnsynchronizedFilterInputStream$Builder";

        CSVParser parser = CSVParser.parse(singleFieldLine, CSVFormat.DEFAULT);
        CSVRecord record = parser.nextRecord();

        // Without a header mapping, a record is always consistent.
        assertTrue("Record should be consistent when no header is defined", record.isConsistent());

        assertEquals("Record should contain exactly one field", 1, record.size());
        assertEquals("This is the first record", 1L, record.getRecordNumber());
        assertEquals("Record starts at the beginning of the stream", 0L, record.getCharacterPosition());
        assertEquals("Record starts at byte offset zero", 0L, record.getBytePosition());
    }
}
