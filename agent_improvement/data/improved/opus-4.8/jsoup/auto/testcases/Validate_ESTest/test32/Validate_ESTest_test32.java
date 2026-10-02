package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test32 extends Validate_ESTest_scaffolding {

    /**
     * Calling the varargs overload {@link Validate#fail(String, Object...)} with a
     * {@code null} argument array makes the internal {@code String.format} call fail
     * with an {@link IllegalArgumentException}, which propagates out of {@code Validate}.
     */
    @Test(timeout = 4000)
    public void failWithNullArgsArrayThrowsIllegalArgumentException() throws Throwable {
        try {
            Validate.fail("org.jsoup.helper.ValidationException", (Object[]) null);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException expected) {
            // The exception must originate from Validate (via its String.format call).
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
