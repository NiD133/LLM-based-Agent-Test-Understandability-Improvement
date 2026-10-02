package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test14 extends Validate_ESTest_scaffolding {

    /**
     * When {@link Validate#isTrue(boolean, String)} is given a false value, it must reject it
     * by throwing an IllegalArgumentException (a ValidationException) carrying the supplied message.
     */
    @Test(timeout = 4000)
    public void isTrueWithFalseValueThrowsIllegalArgumentException() throws Throwable {
        String failureMessage = "W\"N+(;C";

        try {
            Validate.isTrue(false, failureMessage);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // Thrown from Validate because the value was not true.
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
