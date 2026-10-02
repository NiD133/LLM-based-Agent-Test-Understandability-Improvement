package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test21 extends JavaVersion_ESTest_scaffolding {

    private static final String JAVA_14_VERSION_STRING = "14";
    private static final JavaVersion EXPECTED_JAVA_14_VERSION = JavaVersion.JAVA_14;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JavaVersion parsedVersion = JavaVersion.get(JAVA_14_VERSION_STRING);

        assertEquals(EXPECTED_JAVA_14_VERSION, parsedVersion);
    }
}
