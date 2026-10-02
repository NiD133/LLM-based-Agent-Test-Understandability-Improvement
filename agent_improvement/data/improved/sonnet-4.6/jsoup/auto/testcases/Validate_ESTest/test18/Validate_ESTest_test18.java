package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test18 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that {@code Validate.expectNotNull} throws NullPointerException
     * when the checked object is null AND the error-message format string is also null.
     *
     * When the object is null the method delegates to {@code String.format(msg, args)}.
     * Passing a null format string to {@code String.format} causes a NullPointerException,
     * so that exception propagates out of {@code expectNotNull}.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // An array whose first element is null (default value for Object[])
        Object[] formatArgs = new Object[5];
        Object nullObject = formatArgs[0]; // null

        // A null format string will make String.format throw NullPointerException
        String nullFormatMessage = null;

        try {
            Validate.expectNotNull(nullObject, nullFormatMessage, formatArgs);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // String.format(null, ...) throws NullPointerException; no message is attached
        }
    }
}
