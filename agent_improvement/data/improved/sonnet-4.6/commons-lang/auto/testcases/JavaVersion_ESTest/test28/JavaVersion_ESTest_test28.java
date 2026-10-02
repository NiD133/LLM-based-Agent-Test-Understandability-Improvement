package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test28 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withVersionString1_2_returnsJava1_2Constant() throws Throwable {
        JavaVersion result = JavaVersion.get("1.2");
        assertEquals("JavaVersion.get(\"1.2\") should return the JAVA_1_2 enum constant",
                JavaVersion.JAVA_1_2, result);
    }
}
