package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test30 extends Validate_ESTest_scaffolding {

    /**
     * Validate.notNull(null) should reject a null argument by throwing an
     * IllegalArgumentException (a ValidationException) with the message
     * "Object must not be null".
     */
    @Test(timeout = 4000)
    public void notNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // Thrown by Validate because the object argument was null.
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
