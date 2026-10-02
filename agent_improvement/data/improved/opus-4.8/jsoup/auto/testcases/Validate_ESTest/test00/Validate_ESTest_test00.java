package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test00 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmpty rejects an empty string by throwing a ValidationException,
     * which is a subtype of IllegalArgumentException. Here the string to validate is
     * empty (the second argument is the failure message), so the call must fail.
     */
    @Test(timeout = 4000)
    public void notEmpty_withEmptyString_throwsIllegalArgumentException() throws Throwable {
        String emptyStringToValidate = "";
        String failureMessage = "";

        try {
            Validate.notEmpty(emptyStringToValidate, failureMessage);
            fail("Expected an IllegalArgumentException because the string is empty");
        } catch (IllegalArgumentException expected) {
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
