package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test02 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmpty(string, msg) must reject a null string by throwing an
     * IllegalArgumentException (the ValidationException it raises is a subclass).
     */
    @Test(timeout = 4000)
    public void notEmpty_withNullString_throwsIllegalArgumentException() throws Throwable {
        String nullString = null;
        String validationMessage = "Kw>ry`vUEOCJ/7>w'";

        try {
            Validate.notEmpty(nullString, validationMessage);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown from Validate.notEmpty when the string argument is null.
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
