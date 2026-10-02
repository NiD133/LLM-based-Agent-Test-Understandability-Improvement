package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test24 extends Validate_ESTest_scaffolding {

    /**
     * Validate.ensureNotNull rejects a null argument by throwing a
     * ValidationException (a subtype of IllegalArgumentException) with the
     * message "Object must not be null".
     */
    @Test(timeout = 4000)
    public void ensureNotNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.ensureNotNull((Object) null);
            fail("Expected an IllegalArgumentException because the object is null");
        } catch (IllegalArgumentException e) {
            // Thrown from Validate; carries the message "Object must not be null".
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
