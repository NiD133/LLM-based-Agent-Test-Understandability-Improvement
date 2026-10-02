package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test3 extends Regex_ESTest_scaffolding {

    // The regex pattern string used for both compilation and matching
    private static final String PATTERN_STRING = "=;r'm!";

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Wrap an existing JDK Pattern using fromPattern (bypasses re2j selection)
        Pattern jdkPattern = Pattern.compile(PATTERN_STRING);
        Regex regex = Regex.fromPattern(jdkPattern);

        // A matcher created against the same input string should never be null
        Regex.Matcher matcher = regex.matcher(PATTERN_STRING);
        assertNotNull(matcher);
    }
}
