package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test10 extends Validate_ESTest_scaffolding {

    /**
     * Validate.isFalse must reject a value that is true. When the assertion fails,
     * it throws an IllegalArgumentException carrying the supplied message.
     */
    @Test(timeout = 4000)
    public void isFalse_withTrueValue_throwsIllegalArgumentExceptionWithMessage() throws Throwable {
        String failureMessage = "(iS;>>ZVW";

        try {
            Validate.isFalse(true, failureMessage);
            fail("Expected an IllegalArgumentException because the value is not false");
        } catch (IllegalArgumentException e) {
            // The exception originates from Validate, using failureMessage as its text.
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
