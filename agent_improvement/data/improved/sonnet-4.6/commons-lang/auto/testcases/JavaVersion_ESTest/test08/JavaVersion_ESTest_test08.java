package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test08 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that JavaVersion.get("27") resolves to the JAVA_27 constant
     * and that its toString() returns the version string "27".
     */
    @Test(timeout = 4000)
    public void test_get_withJava27VersionString_returnsJava27ConstantWithCorrectToString() throws Throwable {
        JavaVersion java27 = JavaVersion.get("27");
        assertEquals("27", java27.toString());
    }
}
