package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test47 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that JavaVersion.get() throws NumberFormatException when given a
     * string that contains no recognizable version number (e.g. "/u").
     *
     * Internally, get() falls through to the default branch, where it attempts
     * Float.parseFloat() on a substring of the input.  A non-numeric input like
     * "/u" causes Float.parseFloat() to throw NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test47_getWithNonNumericStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/u");
            fail("Expected NumberFormatException for non-numeric version string \"/u\"");
        } catch (NumberFormatException e) {
            // expected: Float.parseFloat cannot parse "/u"
        }
    }
}
