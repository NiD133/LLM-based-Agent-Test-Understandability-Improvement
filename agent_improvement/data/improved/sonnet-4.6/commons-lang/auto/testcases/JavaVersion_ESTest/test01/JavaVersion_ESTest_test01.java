package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test01 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01_atMostReturnsFalseWhenCurrentVersionIsNewer() throws Throwable {
        JavaVersion java25 = JavaVersion.JAVA_25;
        JavaVersion java19 = JavaVersion.get("19");

        // JAVA_25 is newer than JAVA_19, so atMost(JAVA_19) must be false
        boolean java25IsAtMostJava19 = java25.atMost(java19);

        assertFalse(java25IsAtMostJava19);
    }
}
