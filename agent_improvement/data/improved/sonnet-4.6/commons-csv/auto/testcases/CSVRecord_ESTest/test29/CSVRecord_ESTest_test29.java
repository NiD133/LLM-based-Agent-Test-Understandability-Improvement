package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.Locale;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test29 extends CSVRecord_ESTest_scaffolding {

    /**
     * Verifies that a CSVRecord parsed from a single-field CSV string (no comma delimiter)
     * supports spliterator(), and that its position and size metadata are correct.
     *
     * The input "*;Ax}g<" contains no comma (DEFAULT format delimiter), so it is treated
     * as a single field. The record is the first (and only) record in the input, so it
     * starts at byte/character position 0 and has record number 1.
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        // Parse a string that has no commas, so the DEFAULT format treats it as one field
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);
        CSVRecord record = parser.nextRecord();

        // spliterator() must be callable without error (inherited from Iterable<String>)
        record.spliterator();

        // The first (and only) parsed record starts at the beginning of the input
        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());

        // No comma in input means the whole string is one field
        assertEquals(1, record.size());
    }
}
