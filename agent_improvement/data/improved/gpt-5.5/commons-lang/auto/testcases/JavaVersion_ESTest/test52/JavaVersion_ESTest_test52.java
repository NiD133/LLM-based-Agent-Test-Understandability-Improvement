package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test52 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testJava12VersionStringMapsToJava12Constant() throws Throwable {
        JavaVersion actualVersion = JavaVersion.get("12");

        assertEquals("The Java version string should resolve to the Java 12 enum constant.", JavaVersion.JAVA_12, actualVersion);
    }
}
