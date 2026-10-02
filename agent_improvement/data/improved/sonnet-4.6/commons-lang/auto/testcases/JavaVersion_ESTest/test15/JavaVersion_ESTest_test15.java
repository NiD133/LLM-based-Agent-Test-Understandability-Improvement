package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test15 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that looking up Java version "20" by its version string returns
     * an enum constant whose toString() yields the same string "20".
     */
    @Test(timeout = 4000)
    public void test_getByVersionString_java20_toStringReturnsVersionNumber() throws Throwable {
        JavaVersion java20 = JavaVersion.get("20");
        assertEquals("20", java20.toString());
    }
}
