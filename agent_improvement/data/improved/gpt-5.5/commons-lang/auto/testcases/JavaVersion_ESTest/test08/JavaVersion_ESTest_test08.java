package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test08 extends JavaVersion_ESTest_scaffolding {

    private static final String JAVA_27_VERSION = "27";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        JavaVersion javaVersion = JavaVersion.get(JAVA_27_VERSION);

        assertEquals(JAVA_27_VERSION, javaVersion.toString());
    }
}
