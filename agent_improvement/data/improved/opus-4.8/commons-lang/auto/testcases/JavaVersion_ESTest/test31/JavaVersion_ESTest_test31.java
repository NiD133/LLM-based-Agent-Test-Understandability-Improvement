package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test31 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * "1.7" to the {@link JavaVersion#JAVA_1_7} enum constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString1_7ReturnsJava1_7() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("1.7");

        assertEquals(JavaVersion.JAVA_1_7, resolvedVersion);
    }
}
