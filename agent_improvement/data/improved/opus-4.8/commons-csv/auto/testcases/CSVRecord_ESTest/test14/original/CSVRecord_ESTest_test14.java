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
public class CSVRecord_ESTest_test14 extends CSVRecord_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        String[] stringArray0 = new String[5];
        CSVRecord cSVRecord0 = new CSVRecord((CSVParser) null, stringArray0, "h%l{_WoZB#FA_}`", 3988L, 3988L, 3988L);
        Locale.FilteringMode locale_FilteringMode0 = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;
        // Undeclared exception!
        try {
            cSVRecord0.get((Enum<?>) locale_FilteringMode0);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // No header mapping was specified, the record values can't be accessed by name
            //
            verifyException("org.apache.commons.csv.CSVRecord", e);
        }
    }
}
