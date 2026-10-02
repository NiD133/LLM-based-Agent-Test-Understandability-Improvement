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
     * When the re2j engine is disabled, {@link Regex#compile(String)} falls back to the JDK
     * regex engine. Compiling a malformed pattern (here the unclosed character class "[`L")
     * should surface as an IllegalArgumentException thrown by Regex itself.
     */
    @Test(timeout = 4000)
    public void compileWithInvalidPatternThrowsIllegalArgumentException() throws Throwable {
        // Force the JDK regex engine so compilation goes through Pattern.compile.
        Regex.wantsRe2j(false);

        String malformedRegex = "w%-9C1X&IB[`L"; // unclosed character class near index 12

        try {
            Regex.compile(malformedRegex);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The invalid syntax is reported by Regex, not by an inner helper class.
            verifyException("org.jsoup.helper.Regex", e);
        }
    }
}
