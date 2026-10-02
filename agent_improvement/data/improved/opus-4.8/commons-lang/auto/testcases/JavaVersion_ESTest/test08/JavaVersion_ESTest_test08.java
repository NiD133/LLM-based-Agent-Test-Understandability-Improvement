package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test08 extends JavaVersion_ESTest_scaffolding {

    /**
     * Looking up the known version string "27" should resolve to the
     * {@link JavaVersion#JAVA_27} constant, whose {@code toString()} returns
     * the standard name "27".
     */
    @Test(timeout = 4000)
    public void getKnownVersion27ReturnsConstantNamed27() throws Throwable {
        JavaVersion java27 = JavaVersion.get("27");

        assertEquals("27", java27.toString());
    }
}
