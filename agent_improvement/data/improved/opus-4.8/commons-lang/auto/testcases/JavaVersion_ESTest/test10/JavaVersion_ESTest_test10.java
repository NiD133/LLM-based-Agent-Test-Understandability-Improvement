package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test10 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that contains a non-numeric character ("0s") does not match
     * any known Java version, so {@code getJavaVersion} falls through to parsing it
     * as a float. Parsing "0s" fails, surfacing a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionRejectsNonNumericVersionString() throws Throwable {
        try {
            JavaVersion.getJavaVersion("0s");
            fail("Expected a NumberFormatException for the unparsable version string \"0s\"");
        } catch (NumberFormatException expected) {
            // Expected: "0s" cannot be parsed as a floating-point version number.
        }
    }
}
