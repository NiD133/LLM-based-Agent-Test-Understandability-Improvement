package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test40 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test40() throws Throwable {
        final String java25VersionName = "25";
        final JavaVersion parsedVersion = JavaVersion.get(java25VersionName);

        assertEquals(JavaVersion.JAVA_25, parsedVersion);
    }
}
