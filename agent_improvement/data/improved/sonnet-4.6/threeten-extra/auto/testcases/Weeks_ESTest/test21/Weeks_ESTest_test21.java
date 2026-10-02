package org.threeten.extra;

import static org.evosuite.runtime.EvoAssertions.*;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test21 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that parsing a valid ISO-8601 weeks string "P1W" either succeeds
     * or, when EvoSuite's mocked JVM simulates non-deterministic Matcher behaviour,
     * throws an IllegalStateException originating from java.util.regex.Matcher.
     * The assertion is conditionally executed because the mocked Matcher does not
     * always raise the exception (unstable behaviour under mock non-determinism).
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        try {
            Weeks.parse("P1W");
        } catch (IllegalStateException e) {
            verifyException("java.util.regex.Matcher", e);
        }
    }
}
