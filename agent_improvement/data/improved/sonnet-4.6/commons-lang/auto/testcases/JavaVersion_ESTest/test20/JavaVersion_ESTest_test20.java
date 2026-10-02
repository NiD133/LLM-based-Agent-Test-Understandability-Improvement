package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test20 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withJava15VersionString_returnsJava15Constant() throws Throwable {
        // "15" is the java.specification.version value used since Java 15
        JavaVersion result = JavaVersion.get("15");
        assertEquals("JavaVersion.get(\"15\") should return the JAVA_15 enum constant",
                JavaVersion.JAVA_15, result);
    }
}
