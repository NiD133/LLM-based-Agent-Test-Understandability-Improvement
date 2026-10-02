package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test54 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withVersionString10_returnsJava10Constant() throws Throwable {
        // "10" is the version string used by the Java specification for Java 10
        JavaVersion result = JavaVersion.get("10");
        assertEquals(JavaVersion.JAVA_10, result);
    }
}
