package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test08 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notEmpty(String) should reject a null string by throwing an
     * IllegalArgumentException ("String must not be empty").
     */
    @Test(timeout = 4000)
    public void notEmpty_withNullString_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notEmpty((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // Thrown by Validate because the string is null.
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
