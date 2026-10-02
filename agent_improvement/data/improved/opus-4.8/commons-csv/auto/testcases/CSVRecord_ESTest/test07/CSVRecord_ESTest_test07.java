package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test07 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that isSet(null) returns false on a freshly parsed record, and
     * checks the record's positional/metadata accessors for a single-line input.
     */
    @Test(timeout = 4000)
    public void isSetWithNullNameReturnsFalseAndRecordMetadataIsCorrect() throws Throwable {
        final String value = "*;Ax}g<";

        // Build a format whose header declares three columns, all named "*;Ax}g<".
        final CSVFormat.Builder formatBuilder = CSVFormat.Builder.create();
        formatBuilder.setHeader(value, value, value);
        final CSVFormat format = formatBuilder.get();

        // Parse a single line of input and read the first (and only) record.
        final CSVParser parser = CSVParser.parse(value, format);
        final CSVRecord record = parser.nextRecord();

        // A null column name is never mapped, so isSet must be false.
        final boolean nullNameIsSet = record.isSet((String) null);
        assertFalse(nullNameIsSet);

        // The record starts at the beginning of the stream and is the first record.
        assertEquals(0L, record.getBytePosition());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(1L, record.getRecordNumber());
        assertTrue(record.isConsistent());
    }
}
