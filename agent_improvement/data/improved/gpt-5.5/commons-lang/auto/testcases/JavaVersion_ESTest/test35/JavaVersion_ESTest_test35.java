package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test35 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        final String invalidVersionWithDash = "1-R";

        try {
            JavaVersion.getJavaVersion(invalidVersionWithDash);
            fail("Expected NumberFormatException for version: " + invalidVersionWithDash);
        } catch (NumberFormatException expected) {
            // Expected when the minor version segment is not numeric.
        }
    }
}
