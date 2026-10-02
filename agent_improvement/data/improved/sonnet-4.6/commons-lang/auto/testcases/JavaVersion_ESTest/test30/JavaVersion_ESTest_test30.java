package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test30 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withJava18VersionString_returnsJava18Constant() throws Throwable {
        JavaVersion result = JavaVersion.get("1.8");
        assertEquals(JavaVersion.JAVA_1_8, result);
    }
}
