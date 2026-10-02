package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test00 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_atMost_withSameVersion_returnsTrue() throws Throwable {
        JavaVersion java27 = JavaVersion.JAVA_27;
        boolean result = java27.atMost(java27);
        assertTrue("A version should be at most itself (equal versions satisfy atMost)", result);
    }
}
