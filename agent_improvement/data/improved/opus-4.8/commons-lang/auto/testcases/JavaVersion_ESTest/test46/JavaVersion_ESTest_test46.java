package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test46 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a recognized constant and contains no parseable
     * number (here "/v") falls through to JavaVersion.get's default branch, where it
     * is eventually handed to Float.parseFloat. That parse fails, so get propagates a
     * NumberFormatException rather than returning a JavaVersion.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/v");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // expected: "/v" cannot be parsed as a float version number
        }
    }
}
