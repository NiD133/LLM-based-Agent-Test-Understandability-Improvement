package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test25 extends IOCase_ESTest_scaffolding {

    /**
     * IOCase.forName only recognises "Sensitive", "Insensitive", and "System".
     * "$VALUES" is an internal JVM synthetic field name that is not a valid IOCase
     * name, so forName must reject it with an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void test_forName_withInvalidName_throwsIllegalArgumentException() throws Throwable {
        try {
            IOCase.forName("$VALUES");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.IOCase", e);
        }
    }
}
