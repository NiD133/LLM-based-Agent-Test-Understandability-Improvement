package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test22 extends Validate_ESTest_scaffolding {

    /**
     * ensureNotNull(obj, msg, args) must reject a null object by throwing an
     * IllegalArgumentException, even when the format message is empty and the
     * varargs array is null.
     */
    @Test(timeout = 4000)
    public void ensureNotNullWithNullObjectThrowsIllegalArgumentException() throws Throwable {
        Object nullObject = null;
        String emptyMessage = "";
        Object[] nullArgs = null;

        try {
            Validate.ensureNotNull(nullObject, emptyMessage, nullArgs);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
