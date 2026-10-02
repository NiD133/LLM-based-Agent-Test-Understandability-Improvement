package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test44 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that resolving the version string "21" returns the
     * matching {@link JavaVersion#JAVA_21} enum constant.
     */
    @Test(timeout = 4000)
    public void getReturnsJava21ForVersionString21() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("21");

        assertEquals(JavaVersion.JAVA_21, resolvedVersion);
    }
}
