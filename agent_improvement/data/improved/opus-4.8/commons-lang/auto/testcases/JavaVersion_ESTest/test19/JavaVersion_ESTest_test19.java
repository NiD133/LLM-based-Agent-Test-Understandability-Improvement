package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test19 extends JavaVersion_ESTest_scaffolding {

    /**
     * A malformed version string that is not a recognized constant forces
     * {@link JavaVersion#getJavaVersion(String)} into its numeric-parsing
     * fallback. Since "0U" cannot be parsed as a float, the parse should
     * fail with a NumberFormatException rather than returning a value.
     */
    @Test(timeout = 4000)
    public void getJavaVersionWithUnparsableStringThrowsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("0U");
            fail("Expected a NumberFormatException for the unparsable version string \"0U\"");
        } catch (NumberFormatException expected) {
            // Expected: "0U" is not a valid numeric Java version.
        }
    }
}
