package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test33 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_returnsJava15_forVersionString1dot5() throws Throwable {
        // "1.5" is the legacy version string for Java 5; JavaVersion.get should map it to the JAVA_1_5 constant
        JavaVersion result = JavaVersion.get("1.5");
        assertEquals("JavaVersion.get(\"1.5\") should return JAVA_1_5", JavaVersion.JAVA_1_5, result);
    }
}
