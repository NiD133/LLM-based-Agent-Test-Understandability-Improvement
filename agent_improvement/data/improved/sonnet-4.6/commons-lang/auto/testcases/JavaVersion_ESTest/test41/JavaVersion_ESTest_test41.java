package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test41 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withVersionString24_returnsJava24Constant() throws Throwable {
        // JavaVersion.get("24") should map the version string "24" to the JAVA_24 enum constant
        JavaVersion result = JavaVersion.get("24");
        assertEquals(JavaVersion.JAVA_24, result);
    }
}
