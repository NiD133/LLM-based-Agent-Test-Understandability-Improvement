package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test34 extends Validate_ESTest_scaffolding {

    /**
     * Validate.assertFail(...) always raises a failure: it delegates to
     * Validate.fail(msg), which throws a ValidationException (an
     * IllegalArgumentException). The empty message argument does not change
     * this behaviour. The exception is therefore expected even for "".
     */
    @Test(timeout = 4000)
    public void assertFailAlwaysThrowsIllegalArgumentException() throws Throwable {
        try {
            Validate.assertFail("");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The failure must originate from the Validate helper class.
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
