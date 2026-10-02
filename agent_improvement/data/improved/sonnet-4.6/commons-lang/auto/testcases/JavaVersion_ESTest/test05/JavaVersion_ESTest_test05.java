package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test05 extends JavaVersion_ESTest_scaffolding {

    /**
     * "3" is not a recognized Java version string (no switch-case match, float value 3.0
     * is neither < 1 nor > 10), so get() returns null without throwing.
     */
    @Test(timeout = 4000)
    public void test05_getWithUnrecognizedVersionStringReturnsNull() throws Throwable {
        JavaVersion result = JavaVersion.get("3");
        assertNull(result);
    }
}
