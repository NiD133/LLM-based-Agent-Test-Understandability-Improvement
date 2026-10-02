package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test38 extends JavaVersion_ESTest_scaffolding {

    private static final String ANDROID_REPORTED_VERSION = "0.9";
    private static final JavaVersion EXPECTED_ANDROID_VERSION = JavaVersion.JAVA_0_9;

    @Test(timeout = 4000)
    public void getJavaVersionReturnsAndroidVersionForZeroPointNine() throws Throwable {
        JavaVersion actualVersion = JavaVersion.getJavaVersion(ANDROID_REPORTED_VERSION);

        assertEquals(EXPECTED_ANDROID_VERSION, actualVersion);
    }
}
