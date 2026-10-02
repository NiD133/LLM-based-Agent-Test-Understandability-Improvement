package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test49 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#getJavaVersion(String)} throws
     * {@link NumberFormatException} when given a string that begins with a
     * digit but contains a non-numeric character (e.g. {@code "0T"}).
     *
     * <p>Internally, {@code getJavaVersion} delegates to {@code get}, which
     * falls through to the default branch and calls
     * {@link Float#parseFloat(String)} on a substring of the input.  Because
     * {@code "0T"} cannot be parsed as a float, a
     * {@link NumberFormatException} is expected.</p>
     */
    @Test(timeout = 4000)
    public void test49() throws Throwable {
        try {
            JavaVersion.getJavaVersion("0T");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // Expected: "0T" is not a valid version string and causes
            // Float.parseFloat() to throw inside the default branch of get().
        }
    }
}
