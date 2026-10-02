package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Regex_ESTest_test1 extends Regex_ESTest_scaffolding {

    /**
     * When re2j is disabled, {@link Regex#compile(String)} falls back to the JDK regex engine.
     * Compiling a syntactically invalid pattern (here an unclosed character class "[")
     * should be rejected with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void compileInvalidPatternThrowsIllegalArgumentException() throws Throwable {
        // Force the JDK regex engine so compilation goes through Pattern.compile.
        Regex.wantsRe2j(false);

        String invalidPattern = "w%-9C1X&IB[`L"; // '[' opens a character class that is never closed

        try {
            Regex.compile(invalidPattern);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Pattern syntax error: Unclosed character class near index 12"
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
