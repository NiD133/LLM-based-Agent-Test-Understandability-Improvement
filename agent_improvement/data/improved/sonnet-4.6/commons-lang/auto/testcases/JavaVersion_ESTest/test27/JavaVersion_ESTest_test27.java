package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test27 extends JavaVersion_ESTest_scaffolding {

    // JavaVersion.get("1.3") should resolve the version string "1.3" to the JAVA_1_3 enum constant.
    @Test(timeout = 4000)
    public void test_get_returnsJava13_forVersionString1Dot3() throws Throwable {
        JavaVersion result = JavaVersion.get("1.3");
        assertEquals(JavaVersion.JAVA_1_3, result);
    }
}
