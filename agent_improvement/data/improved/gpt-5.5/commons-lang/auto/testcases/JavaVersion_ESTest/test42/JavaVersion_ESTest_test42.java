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

    private static final String JAVA_23_VERSION_STRING = "23";

    @Test(timeout = 4000)
    public void test42() throws Throwable {
        JavaVersion parsedVersion = JavaVersion.get(JAVA_23_VERSION_STRING);

        assertEquals(JavaVersion.JAVA_23, parsedVersion);
    }
}
