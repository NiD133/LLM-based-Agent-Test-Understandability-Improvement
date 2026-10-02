package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test55 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that the version string "9" maps to the {@link JavaVersion#JAVA_9} constant.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withString9_returnsJava9() throws Throwable {
        JavaVersion result = JavaVersion.getJavaVersion("9");

        assertEquals(JavaVersion.JAVA_9, result);
    }
}
