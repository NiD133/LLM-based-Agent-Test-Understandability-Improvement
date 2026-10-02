package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test59 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a recognized constant and is not a parseable
     * number (here {@code "1V"}) falls through to the numeric-parsing branch of
     * {@link JavaVersion#getJavaVersion(String)}, where {@code Float.parseFloat}
     * rejects the non-numeric text and throws a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withNonNumericVersionString_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1V");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // "1V" cannot be parsed as a float version number.
        }
    }
}
