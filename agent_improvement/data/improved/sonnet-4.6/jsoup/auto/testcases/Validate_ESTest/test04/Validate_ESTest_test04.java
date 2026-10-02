package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test04 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that notEmptyParam throws IllegalArgumentException when the string argument is empty.
     * An empty string is invalid for a named parameter, and the exception message should include the parameter name.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        try {
            // Both the string value and the parameter name are empty strings; the empty value triggers the exception.
            Validate.notEmptyParam("", "");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "The '' parameter must not be empty."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
