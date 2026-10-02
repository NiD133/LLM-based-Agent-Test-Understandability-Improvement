package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test32 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that Validate.fail(String, Object...) throws IllegalArgumentException
     * when called with a plain message string and a null args array.
     * The thrown ValidationException (a subclass of IllegalArgumentException) carries
     * the formatted message as its detail text.
     */
    @Test(timeout = 4000)
    public void test_failWithNullArgs_throwsIllegalArgumentException() throws Throwable {
        String message = "org.jsoup.helper.ValidationException";

        try {
            Validate.fail(message, (Object[]) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ValidationException extends IllegalArgumentException;
            // the exception message equals the format string passed to fail().
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
