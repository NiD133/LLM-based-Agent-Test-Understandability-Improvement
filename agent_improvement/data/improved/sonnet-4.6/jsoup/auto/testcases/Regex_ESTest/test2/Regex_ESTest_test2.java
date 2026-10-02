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
public class Regex_ESTest_test2 extends Regex_ESTest_scaffolding {

    // An arbitrary valid regex pattern used to verify round-trip preservation
    private static final String PATTERN_STRING = "&f}HZ;:/IF8@";

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Force the JDK regex engine by disabling re2j
        Regex.wantsRe2j(false);

        // Compile the pattern using the JDK engine
        Regex compiledPattern = Regex.compile(PATTERN_STRING);

        // toString() must return the original pattern string unchanged
        assertEquals(PATTERN_STRING, compiledPattern.toString());
    }
}
