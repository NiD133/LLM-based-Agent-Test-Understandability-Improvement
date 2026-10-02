package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test30 extends JavaVersion_ESTest_scaffolding {

    private static final String JAVA_8_SPECIFICATION_VERSION = "1.8";

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        JavaVersion parsedVersion = JavaVersion.get(JAVA_8_SPECIFICATION_VERSION);

        assertEquals(JavaVersion.JAVA_1_8, parsedVersion);
    }
}
