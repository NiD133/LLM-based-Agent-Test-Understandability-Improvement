package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test2 extends Regex_ESTest_scaffolding {

    /**
     * When compiling with the JDK regex engine (re2j disabled), the compiled
     * Regex should report the original pattern string from its toString().
     */
    @Test(timeout = 4000)
    public void compileWithJdkEngineKeepsOriginalPatternString() throws Throwable {
        String patternText = "&f}HZ;:/IF8@";

        Regex.wantsRe2j(false);
        Regex compiledRegex = Regex.compile(patternText);

        assertEquals(patternText, compiledRegex.toString());
    }
}
