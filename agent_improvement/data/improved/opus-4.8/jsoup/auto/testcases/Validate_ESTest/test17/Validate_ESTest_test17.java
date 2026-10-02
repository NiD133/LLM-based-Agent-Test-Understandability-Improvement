package org.jsoup.helper;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Test for {@link Validate#isTrue(boolean)}.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test17 extends Validate_ESTest_scaffolding {

    /**
     * Calling isTrue with a true value should pass without throwing a ValidationException.
     */
    @Test(timeout = 4000)
    public void isTrueWithTrueValueDoesNotThrow() throws Throwable {
        Validate.isTrue(true);
    }
}
