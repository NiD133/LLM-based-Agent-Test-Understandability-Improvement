package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test5 extends Regex_ESTest_scaffolding {

    /**
     * A Regex built from an existing JDK Pattern should report that pattern's
     * source string via toString().
     */
    @Test(timeout = 4000)
    public void toStringReturnsWrappedPatternSource() throws Throwable {
        Pattern jdkPattern = Pattern.compile("VERTICAL_BAR");

        Regex regex = Regex.fromPattern(jdkPattern);

        assertEquals("VERTICAL_BAR", regex.toString());
    }
}
