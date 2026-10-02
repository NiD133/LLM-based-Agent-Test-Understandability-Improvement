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
     * Validate.notNull(obj, msg) should reject a null object by throwing an
     * IllegalArgumentException (the ValidationException it raises is a subtype).
     * Here both the object and the message are null, so the resulting exception
     * carries no message.
     */
    @Test(timeout = 4000)
    public void notNullWithNullObjectThrowsIllegalArgumentException() throws Throwable {
        try {
            Validate.notNull((Object) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // getMessage() is null because the supplied message was null
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
