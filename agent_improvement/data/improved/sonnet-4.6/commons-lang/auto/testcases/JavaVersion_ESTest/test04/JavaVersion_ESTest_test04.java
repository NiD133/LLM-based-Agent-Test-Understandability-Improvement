package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test04 extends JavaVersion_ESTest_scaffolding {

    /**
     * JavaVersion.get() falls through to the default branch for unrecognised strings.
     * There it calls Float.parseFloat() on the substring after the last '.' character.
     * When the input ends with '.' (as this error-message string does), that substring is
     * empty, which causes Float.parseFloat("") to throw NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test_get_withNonVersionStringEndingInDot_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("Array cannot be empty.");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
