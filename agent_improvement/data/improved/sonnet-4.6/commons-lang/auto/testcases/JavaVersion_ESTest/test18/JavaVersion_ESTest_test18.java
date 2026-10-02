package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test18 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_returnsJava17ForVersionString17() throws Throwable {
        // JavaVersion.get("17") should resolve the version string "17" to the JAVA_17 enum constant,
        // whose toString() returns the canonical version string "17".
        JavaVersion java17 = JavaVersion.get("17");
        assertEquals("17", java17.toString());
    }
}
