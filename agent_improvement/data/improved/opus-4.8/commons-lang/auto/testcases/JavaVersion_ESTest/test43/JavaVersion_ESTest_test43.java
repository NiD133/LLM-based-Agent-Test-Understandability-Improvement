package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test43 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string that is not a recognized constant and cannot be parsed as a
     * number (here "1Q") falls through to the numeric-parsing branch of
     * {@code getJavaVersion}, where {@code Float.parseFloat} rejects the input and
     * throws a {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void getJavaVersionThrowsNumberFormatExceptionForUnparsableVersion() {
        try {
            JavaVersion.getJavaVersion("1Q");
            fail("Expected a NumberFormatException for the unparsable version string \"1Q\"");
        } catch (NumberFormatException expected) {
            // Expected: "1Q" is not a valid float.
        }
    }
}
