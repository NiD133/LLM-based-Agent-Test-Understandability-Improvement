package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test38 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that the version string "0.9" (the version reported by Android)
     * maps to the {@link JavaVersion#JAVA_0_9} enum constant.
     */
    @Test(timeout = 4000)
    public void getJavaVersion_withString0_9_returnsJava0_9() throws Throwable {
        JavaVersion resolved = JavaVersion.getJavaVersion("0.9");

        assertEquals(JavaVersion.JAVA_0_9, resolved);
    }
}
