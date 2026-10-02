package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test28 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that {@code notNullParam} throws {@link IllegalArgumentException} when both
     * the object under validation and the parameter name are {@code null}.
     * The method formats the exception message using the param name, so passing {@code null}
     * as the param name results in the literal string "null" appearing in the message:
     * "The parameter 'null' must not be null."
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        try {
            Validate.notNullParam((Object) null, (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Exception message will be: "The parameter 'null' must not be null."
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
