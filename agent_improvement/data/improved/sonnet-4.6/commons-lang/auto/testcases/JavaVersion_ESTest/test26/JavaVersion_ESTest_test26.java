package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test26 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that getJavaVersion throws NumberFormatException when given a
     * string that is not a valid version number. The input "/kS" reaches the
     * float-parsing branch inside get() and fails at Float.parseFloat().
     */
    @Test(timeout = 4000)
    public void test_getJavaVersion_throwsNumberFormatException_forNonNumericInput() throws Throwable {
        try {
            JavaVersion.getJavaVersion("/kS");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
            // expected — "/kS" cannot be parsed as a float version string
        }
    }
}
