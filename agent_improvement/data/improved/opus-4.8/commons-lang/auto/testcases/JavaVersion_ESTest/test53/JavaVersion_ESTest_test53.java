package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test53 extends JavaVersion_ESTest_scaffolding {

    /**
     * An unrecognized version string that is neither a known constant nor a
     * parseable number falls through to JavaVersion.get's default branch, where
     * it is handed to Float.parseFloat and triggers a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getWithNonNumericVersionStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/o");
            fail("Expected NumberFormatException for non-numeric version string \"/o\"");
        } catch (NumberFormatException expected) {
            // Float.parseFloat cannot parse the non-numeric substring "/o".
        }
    }
}
