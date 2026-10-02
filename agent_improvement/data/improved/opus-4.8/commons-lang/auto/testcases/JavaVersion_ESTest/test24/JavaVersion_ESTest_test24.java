package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test24 extends JavaVersion_ESTest_scaffolding {

    /**
     * "0O" is not a recognized version string, so JavaVersion.get falls through to the
     * default branch where it attempts to parse the value as a float. Because "0O"
     * (zero followed by the letter O) is not a valid number, the parse fails with a
     * NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getRejectsNonNumericVersionString() throws Throwable {
        try {
            JavaVersion.get("0O");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // expected: "0O" cannot be parsed as a float version number
        }
    }
}
