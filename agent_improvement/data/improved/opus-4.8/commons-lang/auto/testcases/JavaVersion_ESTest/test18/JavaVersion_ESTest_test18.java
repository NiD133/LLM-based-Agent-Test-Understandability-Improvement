package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test18 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string "17"
     * to the JAVA_17 constant, whose {@code toString()} returns its standard name "17".
     */
    @Test(timeout = 4000)
    public void getWithVersion17ReturnsConstantNamed17() throws Throwable {
        JavaVersion java17 = JavaVersion.get("17");

        assertEquals("17", java17.toString());
    }
}
