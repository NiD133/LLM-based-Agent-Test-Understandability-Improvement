package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test12 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that looks numeric but contains a non-digit suffix ("1R")
     * cannot be parsed into a float, so {@link JavaVersion#getJavaVersion(String)}
     * is expected to fail with a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionThrowsOnUnparsableVersionString() throws Throwable {
        String unparsableVersion = "1R";

        try {
            JavaVersion.getJavaVersion(unparsableVersion);
            fail("Expected a NumberFormatException for version string \"" + unparsableVersion + "\"");
        } catch (NumberFormatException expected) {
            // Parsing "1R" as a float fails, which is the expected behaviour.
        }
    }
}
