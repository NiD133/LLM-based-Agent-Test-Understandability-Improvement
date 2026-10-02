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
public class CSVRecord_ESTest_test18 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        CSVRecord cSVRecord0 = new CSVRecord((CSVParser) null, (String[]) null, "h%l{_WoZB#FA_}`", 3988L, 3988L, (-977L));
        assertEquals((-977L), cSVRecord0.getBytePosition());
        assertEquals(3988L, cSVRecord0.getCharacterPosition());
        assertEquals(3988L, cSVRecord0.getRecordNumber());
    }
}
