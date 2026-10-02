package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test13 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_singleFieldRecord_hasNoCommentAndStartsAtPositionZero() throws Throwable {
        // Parse a single-field string (no commas, so DEFAULT format produces one field)
        CSVFormat defaultFormat = CSVFormat.DEFAULT;
        CSVParser parser = CSVParser.parse("*;Ax}g<", defaultFormat);
        CSVRecord record = parser.nextRecord();

        // Record should start at byte and character position 0 (beginning of input)
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());

        // This is the first (and only) record in the input
        assertEquals(1L, record.getRecordNumber());

        // The input has no commas, so the entire string is one field
        assertEquals(1, record.size());

        // DEFAULT format has no comment marker configured, so the record has no comment
        boolean hasComment = record.hasComment();
        assertFalse(hasComment);
    }
}
