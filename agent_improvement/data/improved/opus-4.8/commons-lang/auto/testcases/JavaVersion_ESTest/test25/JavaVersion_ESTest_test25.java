package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test25 extends JavaVersion_ESTest_scaffolding {

    /**
     * A malformed version string such as "1-U" is not one of the known version
     * constants, so {@link JavaVersion#getJavaVersion(String)} falls back to
     * parsing it as a float. Because the string is not numeric, the underlying
     * {@code Float.parseFloat} call fails with a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithNonNumericStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1-U");
            fail("Expected a NumberFormatException for the non-numeric version string \"1-U\"");
        } catch (NumberFormatException expected) {
            // Expected: "1-U" cannot be parsed into a float version.
        }
    }
}
