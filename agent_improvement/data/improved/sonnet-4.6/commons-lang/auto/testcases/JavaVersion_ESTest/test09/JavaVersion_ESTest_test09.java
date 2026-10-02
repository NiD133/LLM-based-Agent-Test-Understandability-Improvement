package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test09 extends JavaVersion_ESTest_scaffolding {

    /**
     * "1U" reaches the default branch of JavaVersion.get(), where the code attempts
     * Float.parseFloat on the non-numeric suffix, producing a NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        try {
            JavaVersion.get("1U");
            fail("Expected NumberFormatException for malformed version string '1U'");
        } catch (NumberFormatException e) {
            // expected: non-numeric character 'U' cannot be parsed as a float
        }
    }
}
