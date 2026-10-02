package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test22 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * {@code "13"} to the {@link JavaVersion#JAVA_13} enum constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString13ReturnsJava13() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("13");

        assertEquals(JavaVersion.JAVA_13, resolvedVersion);
    }
}
