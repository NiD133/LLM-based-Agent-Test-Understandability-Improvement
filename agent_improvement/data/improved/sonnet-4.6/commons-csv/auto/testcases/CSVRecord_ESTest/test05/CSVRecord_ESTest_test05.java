package org.apache.commons.csv;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CSVRecord_ESTest_test05 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String[] values = new String[2];
        CSVRecord record = new CSVRecord((CSVParser) null, values, "", 1105L, 0L, 0L);

        // A negative index is always out of bounds, so isSet should return false
        boolean isNegativeIndexSet = record.isSet(-2522);

        assertFalse(isNegativeIndexSet);
        assertEquals(1105L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(2, record.size());
        assertEquals(0L, record.getBytePosition());
    }
}
