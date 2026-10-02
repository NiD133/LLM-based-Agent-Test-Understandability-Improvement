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
     * Validate.wtf() is documented to always throw IllegalStateException.
     * When called with a null message, the exception is still thrown (with a null detail message).
     */
    @Test(timeout = 4000)
    public void wtf_withNullMessage_throwsIllegalStateException() throws Throwable {
        try {
            Validate.wtf(null);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
