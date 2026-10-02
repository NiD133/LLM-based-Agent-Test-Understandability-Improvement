package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test11 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string such as "1S" is not a recognized constant, so JavaVersion.get
     * falls through to parsing it as a float. Because "1S" is not a valid number,
     * the parse fails with a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("1S");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // expected: "1S" cannot be parsed as a float version
        }
    }
}
