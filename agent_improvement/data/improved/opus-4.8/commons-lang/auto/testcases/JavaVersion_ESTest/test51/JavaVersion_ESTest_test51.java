package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test51 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a valid number ("/q") cannot be parsed as a
     * float, so {@link JavaVersion#getJavaVersion(String)} fails internally with
     * a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithNonNumericStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("/q");
            fail("Expected a NumberFormatException for the non-numeric version string \"/q\"");
        } catch (NumberFormatException expected) {
            // The malformed version string cannot be parsed into a float.
        }
    }
}
