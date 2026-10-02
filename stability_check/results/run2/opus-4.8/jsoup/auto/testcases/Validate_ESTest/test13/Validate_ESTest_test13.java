package org.jsoup.helper;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test13 extends Validate_ESTest_scaffolding {

    /**
     * Validate.isFalse(false) accepts a false value, so it should return
     * normally without throwing a ValidationException.
     */
    @Test(timeout = 4000)
    public void isFalseWithFalseValueDoesNotThrow() throws Throwable {
        Validate.isFalse(false);
    }
}
