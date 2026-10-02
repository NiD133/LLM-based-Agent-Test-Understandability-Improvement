package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test26 extends Validate_ESTest_scaffolding {

    /**
     * When both the object and the custom message passed to notNull() are null,
     * the method should still throw an IllegalArgumentException (with a null message),
     * because the null object always triggers the validation failure regardless of
     * whether a meaningful error message was supplied.
     */
    @Test(timeout = 4000)
    public void test26_notNullWithNullObjectAndNullMessage_throwsIllegalArgumentExceptionWithNullMessage() throws Throwable {
        Object nullObject = null;
        String nullMessage = null;

        try {
            Validate.notNull(nullObject, nullMessage);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ValidationException wraps a null message when the caller provides no error text,
            // so getMessage() returns null here
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
