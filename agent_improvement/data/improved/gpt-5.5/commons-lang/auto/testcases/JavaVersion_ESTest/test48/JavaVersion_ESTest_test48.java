package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test48 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test48() throws Throwable {
        final String java16VersionString = "16";
        final JavaVersion expectedJavaVersion = JavaVersion.JAVA_16;

        final JavaVersion actualJavaVersion = JavaVersion.get(java16VersionString);

        assertEquals(expectedJavaVersion, actualJavaVersion);
    }
}
