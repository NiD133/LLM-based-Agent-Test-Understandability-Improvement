package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test15 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that looking up the version string "20" returns the JAVA_20
     * constant, whose {@code toString()} reports the standard name "20".
     */
    @Test(timeout = 4000)
    public void getReturnsJava20ConstantNamedTwenty() throws Throwable {
        JavaVersion java20 = JavaVersion.get("20");

        assertEquals("20", java20.toString());
    }
}
