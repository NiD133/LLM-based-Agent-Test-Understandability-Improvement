package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test12 extends JavaVersion_ESTest_scaffolding {

    private static final String MALFORMED_LEGACY_VERSION = "1R";

    @Test(timeout = 4000)
    public void getJavaVersionRejectsMalformedLegacyVersion() throws Throwable {
        try {
            JavaVersion.getJavaVersion(MALFORMED_LEGACY_VERSION);
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // Expected for legacy-style versions whose minor component is not numeric.
        }
    }
}
