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
     * Verifies that Validate.notNull() throws an IllegalArgumentException
     * when a null object is passed, with the message "Object must not be null".
     */
    @Test(timeout = 4000)
    public void notNull_withNullArgument_throwsIllegalArgumentException() throws Throwable {
        try {
            Validate.notNull((Object) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
