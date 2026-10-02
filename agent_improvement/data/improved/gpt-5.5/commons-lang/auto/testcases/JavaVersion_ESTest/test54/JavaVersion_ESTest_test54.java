package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test54 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test54() throws Throwable {
        final JavaVersion expectedVersion = JavaVersion.JAVA_10;
        final JavaVersion actualVersion = JavaVersion.get("10");

        assertEquals(expectedVersion, actualVersion);
    }
}
