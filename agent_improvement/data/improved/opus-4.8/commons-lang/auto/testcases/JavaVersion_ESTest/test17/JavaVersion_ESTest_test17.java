package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test17 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#get(String)} maps the version string
     * {@code "18"} to the {@code JAVA_18} constant, whose {@code toString()}
     * returns its standard name {@code "18"}.
     */
    @Test(timeout = 4000)
    public void getWithVersion18ReturnsJava18() throws Throwable {
        JavaVersion java18 = JavaVersion.get("18");

        assertEquals("18", java18.toString());
    }
}
