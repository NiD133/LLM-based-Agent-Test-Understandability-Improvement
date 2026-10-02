package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test04 extends JavaVersion_ESTest_scaffolding {

    private static final String NON_NUMERIC_VERSION = "Array cannot be empty.";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        try {
            JavaVersion.get(NON_NUMERIC_VERSION);
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException expected) {
            // Expected: JavaVersion attempts to parse the non-numeric version text.
        }
    }
}
