package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test16 extends Validate_ESTest_scaffolding {

    /**
     * Validate.isTrue(false) must throw IllegalArgumentException with message "Must be true",
     * because the method enforces that the boolean argument must be true.
     */
    @Test(timeout = 4000)
    public void isTrue_withFalseValue_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.isTrue(false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
