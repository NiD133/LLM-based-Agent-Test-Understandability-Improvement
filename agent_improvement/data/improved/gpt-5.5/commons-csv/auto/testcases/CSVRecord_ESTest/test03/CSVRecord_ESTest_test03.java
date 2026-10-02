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
public class CSVRecord_ESTest_test03 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        String[] recordValues = new String[2];
        CSVRecord record = new CSVRecord((CSVParser) null, recordValues, "", 1105L, 1491L, 2147483639L);

        boolean hasSecondColumn = record.isSet(1);

        assertEquals(1491L, record.getCharacterPosition());
        assertTrue(hasSecondColumn);
        assertEquals(1105L, record.getRecordNumber());
        assertEquals(2147483639L, record.getBytePosition());
    }
}
