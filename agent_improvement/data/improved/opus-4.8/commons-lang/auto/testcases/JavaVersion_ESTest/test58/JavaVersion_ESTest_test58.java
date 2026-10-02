package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test58 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#toString()} returns the standard name
     * of the version, which for {@code JAVA_1_2} is {@code "1.2"}.
     */
    @Test(timeout = 4000)
    public void toString_forJava1_2_returnsStandardName() throws Throwable {
        JavaVersion java12 = JavaVersion.JAVA_1_2;

        String standardName = java12.toString();

        assertEquals("1.2", standardName);
    }
}
