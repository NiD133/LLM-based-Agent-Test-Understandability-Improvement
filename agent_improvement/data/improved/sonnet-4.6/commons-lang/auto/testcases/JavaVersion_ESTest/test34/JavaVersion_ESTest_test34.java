package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test34 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_returnsJava14_whenVersionStringIs14() throws Throwable {
        // "1.4" is the version string used by the java.specification.version property for Java 1.4
        JavaVersion result = JavaVersion.get("1.4");
        assertEquals(JavaVersion.JAVA_1_4, result);
    }
}
