package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test07 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmpty rejects an empty string by throwing an
     * IllegalArgumentException with the message "String must not be empty".
     */
    @Test(timeout = 4000)
    public void notEmpty_withEmptyString_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmpty("");
            fail("Expected an IllegalArgumentException for an empty string");
        } catch (IllegalArgumentException expected) {
            // Message: "String must not be empty"
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
