package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test09 extends CSVRecord_ESTest_scaffolding {

    /**
     * Parses a single-column input under a format whose header repeats the same
     * name three times. Because the header map collapses duplicate names into a
     * single entry, its size (1) matches the record's value count (1), so the
     * record is reported as consistent. Also verifies the positional metadata of
     * the first parsed record.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        String duplicatedHeaderName = "*;Ax}g<";
        String[] header = { duplicatedHeaderName, duplicatedHeaderName, duplicatedHeaderName };

        CSVFormat format = CSVFormat.Builder.create()
                .setHeader(header)
                .get();

        CSVParser parser = CSVParser.parse("*;Ax}g<", format);
        CSVRecord firstRecord = parser.nextRecord();

        boolean consistent = firstRecord.isConsistent();

        assertEquals(1L, firstRecord.getRecordNumber());
        assertEquals(0L, firstRecord.getCharacterPosition());
        assertEquals(0L, firstRecord.getBytePosition());
        assertTrue(consistent);
    }
}
