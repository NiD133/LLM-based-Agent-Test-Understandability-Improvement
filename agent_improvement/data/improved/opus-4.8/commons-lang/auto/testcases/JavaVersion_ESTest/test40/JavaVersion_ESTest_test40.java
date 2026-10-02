package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test40 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * "25" to the {@link JavaVersion#JAVA_25} enum constant.
     */
    @Test(timeout = 4000)
    public void getWithVersionString25ReturnsJava25() throws Throwable {
        JavaVersion parsedVersion = JavaVersion.get("25");

        assertEquals(JavaVersion.JAVA_25, parsedVersion);
    }
}
