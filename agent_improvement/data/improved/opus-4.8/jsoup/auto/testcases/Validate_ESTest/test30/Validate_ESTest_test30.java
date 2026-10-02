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
     * Validate.notNull(null) should reject the null argument by throwing an
     * IllegalArgumentException carrying the "Object must not be null" message.
     */
    @Test(timeout = 4000)
    public void notNull_withNullObject_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown from Validate because the object is null ("Object must not be null").
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
