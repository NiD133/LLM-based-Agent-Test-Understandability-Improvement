package org.jsoup.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

/**
 * Test that {@link Validate#notEmpty(String)} accepts a non-empty string
 * without throwing a {@link ValidationException}.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test06 extends Validate_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void notEmpty_withNonEmptyString_doesNotThrow() throws Throwable {
        // A non-empty string is valid input, so no exception is expected.
        Validate.notEmpty("yr`o{,Pr'v!D5M");
    }
}
