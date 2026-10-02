package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test42 extends JavaVersion_ESTest_scaffolding {

    // JavaVersion.get("23") should resolve to the JAVA_23 enum constant
    @Test(timeout = 4000)
    public void test42_getVersionString23ReturnsJava23Constant() throws Throwable {
        JavaVersion resolvedVersion = JavaVersion.get("23");
        assertEquals(JavaVersion.JAVA_23, resolvedVersion);
    }
}
