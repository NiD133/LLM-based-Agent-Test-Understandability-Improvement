package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test09 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // Three identical header names collapse to a single map entry due to duplicate-key semantics
        final String headerAndValue = "*;Ax}g<";
        String[] duplicateHeaders = new String[] { headerAndValue, headerAndValue, headerAndValue };

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(duplicateHeaders)
                .get();

        // Parse a single-field record whose value matches the header string
        CSVParser parser = CSVParser.parse(headerAndValue, format);
        CSVRecord record = parser.nextRecord();

        // The header map has 1 entry (duplicates merged) and the record has 1 value, so the record is consistent
        boolean isConsistent = record.isConsistent();

        assertEquals(1L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(0L, record.getBytePosition());
        assertTrue(isConsistent);
    }
}
