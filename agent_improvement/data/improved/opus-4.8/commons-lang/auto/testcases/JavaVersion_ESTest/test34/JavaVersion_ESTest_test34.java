package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test34 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * "1.4" to the {@link JavaVersion#JAVA_1_4} enum constant.
     */
    @Test(timeout = 4000)
    public void getReturnsJava14ForVersionString14() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("1.4");

        assertEquals(JavaVersion.JAVA_1_4, resolvedVersion);
    }
}
