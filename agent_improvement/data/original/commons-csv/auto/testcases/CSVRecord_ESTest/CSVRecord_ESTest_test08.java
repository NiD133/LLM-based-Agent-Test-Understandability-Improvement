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
public class CSVRecord_ESTest_test08 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        String[] stringArray0 = new String[2];
        CSVRecord cSVRecord0 = new CSVRecord((CSVParser) null, stringArray0, "", 1105L, 0L, 0L);
        boolean boolean0 = cSVRecord0.isSet("");
        assertEquals(0L, cSVRecord0.getBytePosition());
        assertEquals(1105L, cSVRecord0.getRecordNumber());
        assertFalse(boolean0);
        assertEquals(0L, cSVRecord0.getCharacterPosition());
        assertEquals(2, cSVRecord0.size());
    }
}
