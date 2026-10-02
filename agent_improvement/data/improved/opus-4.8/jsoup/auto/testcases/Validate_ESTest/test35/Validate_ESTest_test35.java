package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test35 extends Validate_ESTest_scaffolding {

    /**
     * Validate.wtf(msg) always signals an unexpected state by throwing an
     * IllegalStateException. When the supplied message is null, the thrown
     * exception carries no message (getMessage() returns null).
     */
    @Test(timeout = 4000)
    public void wtf_withNullMessage_throwsIllegalStateExceptionWithNoMessage() throws Throwable {
        try {
            Validate.wtf((String) null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException expected) {
            // The exception originates from Validate and has no message.
            assertNull(expected.getMessage());
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
