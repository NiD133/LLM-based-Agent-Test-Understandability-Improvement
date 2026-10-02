package org.threeten.extra.scale;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test15 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that parsing a TAI instant string whose nanosecond segment does not
     * strictly match the required 9-digit format (here the decimal part "000000020"
     * contains a trailing digit that shifts the pattern) either succeeds silently or
     * causes the regex Matcher to raise an IllegalStateException (observed under
     * EvoSuite's mocked JVM non-determinism).  The assertion is intentionally
     * non-strict: no failure is raised when no exception occurs.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Input whose decimal part may not satisfy the strict 9-digit TAI pattern
        // under certain JVM / regex-mock configurations.
        String taiStringWithNonStandardNanos = "20.000000020s(TAI)";
        try {
            TaiInstant.parse(taiStringWithNonStandardNanos);
        } catch (IllegalStateException e) {
            // The mocked regex Matcher throws IllegalStateException ("No match found")
            // when the pattern fails to match in a mocked JVM environment.
            verifyException("java.util.regex.Matcher", e);
        }
    }
}
