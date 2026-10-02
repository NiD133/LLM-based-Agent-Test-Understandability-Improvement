package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test22 extends Years_ESTest_scaffolding {

    /**
     * Verifies that parsing the valid ISO-8601 zero-years string "P0Y" either
     * succeeds without throwing, or — under the EvoSuite mock JVM — throws an
     * IllegalStateException originating from java.util.regex.Matcher.
     *
     * The test does not assert a positive outcome when parsing succeeds; it only
     * guards that any exception that does arise comes from the Matcher and not
     * from unrelated code.
     */
    @Test(timeout = 4000)
    public void test_parseZeroYears_succeedsOrThrowsMatcherIllegalState() throws Throwable {
        try {
            Years.parse("P0Y");
        } catch (IllegalStateException e) {
            verifyException("java.util.regex.Matcher", e);
        }
    }
}
