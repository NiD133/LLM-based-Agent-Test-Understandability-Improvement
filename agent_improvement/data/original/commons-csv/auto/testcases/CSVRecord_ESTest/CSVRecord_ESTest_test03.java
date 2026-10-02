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
        String[] stringArray0 = new String[2];
        CSVRecord cSVRecord0 = new CSVRecord((CSVParser) null, stringArray0, "", 1105L, 1491L, 2147483639L);
        boolean boolean0 = cSVRecord0.isSet(1);
        assertEquals(1491L, cSVRecord0.getCharacterPosition());
        assertTrue(boolean0);
        assertEquals(1105L, cSVRecord0.getRecordNumber());
        assertEquals(2147483639L, cSVRecord0.getBytePosition());
    }
}
