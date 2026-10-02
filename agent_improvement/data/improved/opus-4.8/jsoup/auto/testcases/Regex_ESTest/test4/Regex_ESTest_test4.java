package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test4 extends Regex_ESTest_scaffolding {

    /**
     * A Regex compiled by {@link Regex#compile(String)} should report that it
     * uses the re2j engine when re2j is available and enabled on the classpath.
     */
    @Test(timeout = 4000)
    public void compiledRegexUsesRe2jEngine() throws Throwable {
        Regex compiledRegex = Regex.compile("VERTICAL_BAR");

        assertTrue(
            "Expected the compiled regex to use the re2j engine",
            compiledRegex.usingRe2j());
    }
}
