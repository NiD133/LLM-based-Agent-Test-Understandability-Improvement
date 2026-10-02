package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test07 extends JavaVersion_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testGetWithUnrecognizedVersionStringReturnsNull() throws Throwable {
        // "0" is not a recognized Java version string (valid versions start at "0.9"),
        // so get() should return null without throwing an exception.
        JavaVersion result = JavaVersion.get("0");
        assertNull(result);
    }
}
