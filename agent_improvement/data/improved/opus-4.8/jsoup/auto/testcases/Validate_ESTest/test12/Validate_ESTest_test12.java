package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test12 extends Validate_ESTest_scaffolding {

    /**
     * Validate.isFalse(boolean) must reject a {@code true} value by throwing
     * an IllegalArgumentException (the ValidationException subtype) with the
     * message "Must be false".
     */
    @Test(timeout = 4000)
    public void isFalse_whenValueIsTrue_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.isFalse(true);
            fail("Expected an IllegalArgumentException because the value is not false");
        } catch (IllegalArgumentException expected) {
            // Validate.isFalse throws with the message "Must be false".
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
