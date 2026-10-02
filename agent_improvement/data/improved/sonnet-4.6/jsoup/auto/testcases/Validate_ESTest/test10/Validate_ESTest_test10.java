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

    private static final String ERROR_MESSAGE = "(iS;>>ZVW";

    // Validate.isFalse(true, msg) must throw an IllegalArgumentException whose
    // message equals the supplied string, because the condition is not false.
    @Test(timeout = 4000)
    public void test_isFalse_withTrueValue_throwsIllegalArgumentExceptionWithMessage() throws Throwable {
        try {
            Validate.isFalse(true, ERROR_MESSAGE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
