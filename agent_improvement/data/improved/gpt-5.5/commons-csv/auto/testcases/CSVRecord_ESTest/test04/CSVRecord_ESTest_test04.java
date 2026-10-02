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
public class CSVRecord_ESTest_test04 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final String[] recordValues = new String[2];
        final CSVRecord record = new CSVRecord((CSVParser) null, recordValues, "", 1105L, 0L, 0L);

        final boolean hasValueAtOutOfRangeIndex = record.isSet(3174);

        assertEquals(1105L, record.getRecordNumber());
        assertEquals(0L, record.getCharacterPosition());
        assertEquals(2, record.size());
        assertFalse(hasValueAtOutOfRangeIndex);
        assertEquals(0L, record.getBytePosition());
    }
}
