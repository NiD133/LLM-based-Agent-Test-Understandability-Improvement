package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test38 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getJavaVersion_returnsJava09_forAndroidVersionString() throws Throwable {
        // "0.9" is the non-standard version string reported by Android devices
        JavaVersion result = JavaVersion.getJavaVersion("0.9");
        assertEquals(JavaVersion.JAVA_0_9, result);
    }
}
