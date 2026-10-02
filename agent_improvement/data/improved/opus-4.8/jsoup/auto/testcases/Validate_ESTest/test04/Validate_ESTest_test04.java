package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test04 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmptyParam rejects an empty string argument by throwing an
     * IllegalArgumentException (ValidationException). Here both the value and the
     * parameter name are empty, so the validation must fail.
     */
    @Test(timeout = 4000)
    public void notEmptyParamWithEmptyStringThrowsException() throws Throwable {
        String emptyValue = "";
        String emptyParamName = "";

        try {
            Validate.notEmptyParam(emptyValue, emptyParamName);
            fail("Expected an IllegalArgumentException because the value is empty");
        } catch (IllegalArgumentException expected) {
            // The exception must originate from the Validate helper class.
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
