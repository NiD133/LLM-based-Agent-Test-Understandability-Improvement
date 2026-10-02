package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test41 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * "24" to the {@link JavaVersion#JAVA_24} enum constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString24ReturnsJava24() throws Throwable {
        JavaVersion result = JavaVersion.get("24");

        assertEquals(JavaVersion.JAVA_24, result);
    }
}
