package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test23 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that resolving the version string "11" returns the JAVA_11 constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString11ReturnsJava11() throws Throwable {
        JavaVersion resolved = JavaVersion.get("11");

        assertEquals(JavaVersion.JAVA_11, resolved);
    }
}
