package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test58 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_Java12_toStringReturnsVersionString() throws Throwable {
        // JavaVersion.toString() should return the standard version string (e.g. "1.2")
        // rather than the enum constant name (e.g. "JAVA_1_2")
        String versionString = JavaVersion.JAVA_1_2.toString();
        assertEquals("1.2", versionString);
    }
}
