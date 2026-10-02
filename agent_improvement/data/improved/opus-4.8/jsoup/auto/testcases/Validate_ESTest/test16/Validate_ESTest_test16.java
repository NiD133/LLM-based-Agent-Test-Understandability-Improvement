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
     * Validate.isTrue(false) should reject the false value by throwing an
     * IllegalArgumentException (the ValidationException raised carries the
     * "Must be true" message).
     */
    @Test(timeout = 4000)
    public void isTrueWithFalseThrowsIllegalArgumentException() throws Throwable {
        try {
            Validate.isTrue(false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // The exception must originate from Validate (message: "Must be true").
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
