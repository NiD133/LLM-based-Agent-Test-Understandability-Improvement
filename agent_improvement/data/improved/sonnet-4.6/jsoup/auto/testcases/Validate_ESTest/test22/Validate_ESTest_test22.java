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
     * Verifies that {@code ensureNotNull} throws {@link IllegalArgumentException}
     * when the supplied object is {@code null}, even if the format-args vararg is
     * explicitly passed as {@code null}.
     *
     * <p>Calling {@code ensureNotNull(null, "", null)} triggers the null-check
     * inside the deprecated overload that formats a message via
     * {@code String.format(msg, args)}. The first argument being {@code null}
     * causes the method to throw before any formatting takes place.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Object nullObject = null;
        String emptyFormatMessage = "";
        Object[] nullArgs = null;

        try {
            Validate.ensureNotNull(nullObject, emptyFormatMessage, nullArgs);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
