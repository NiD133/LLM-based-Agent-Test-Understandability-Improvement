package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test05 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that {@link Validate#notEmptyParam(String, String)} rejects a null
     * string argument by throwing an IllegalArgumentException (ValidationException).
     */
    @Test(timeout = 4000)
    public void notEmptyParam_withNullString_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmptyParam((String) null, (String) null);
            fail("Expected an IllegalArgumentException because the string parameter is null");
        } catch (IllegalArgumentException expected) {
            // The exception must originate from Validate.
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
