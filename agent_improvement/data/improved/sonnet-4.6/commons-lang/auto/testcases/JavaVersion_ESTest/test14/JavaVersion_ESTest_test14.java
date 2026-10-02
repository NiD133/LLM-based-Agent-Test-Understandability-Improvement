package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test14 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version string like "1P" passes the initial decimal-check branch inside
     * JavaVersion.get() and eventually reaches Float.parseFloat("1P"), which
     * cannot parse the non-numeric character and throws NumberFormatException.
     */
    @Test(timeout = 4000)
    public void test14_getJavaVersion_withAlphanumericInput_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.getJavaVersion("1P");
            fail("Expecting exception: NumberFormatException");
        } catch (NumberFormatException e) {
        }
    }
}
