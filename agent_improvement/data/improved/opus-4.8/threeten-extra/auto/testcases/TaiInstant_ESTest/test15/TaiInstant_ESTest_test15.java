package org.threeten.extra.scale;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test15 extends TaiInstant_ESTest_scaffolding {

    /**
     * Parsing a TAI instant string may surface an {@link IllegalStateException}
     * raised by the underlying regex {@code Matcher} ("No match found") when the
     * mocked, non-deterministic JVM environment disrupts the match. The exception
     * is optional, so the test only asserts its origin when it actually occurs.
     */
    @Test(timeout = 4000)
    public void parseMayThrowMatcherIllegalStateException() throws Throwable {
        try {
            TaiInstant.parse("20.000000020s(TAI)");
        } catch (IllegalStateException expectedFromMatcher) {
            verifyException("java.util.regex.Matcher", expectedFromMatcher);
        }
    }
}
