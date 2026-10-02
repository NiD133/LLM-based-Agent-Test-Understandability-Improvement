package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test27 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * "1.3" to the {@link JavaVersion#JAVA_1_3} enum constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString1_3ReturnsJava1_3() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("1.3");

        assertEquals(JavaVersion.JAVA_1_3, resolvedVersion);
    }
}
