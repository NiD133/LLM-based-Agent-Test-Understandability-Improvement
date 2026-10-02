package org.jsoup.helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test13 extends Validate_ESTest_scaffolding {

    /**
     * Validate.isFalse accepts a false value without throwing:
     * the validation only fails when the argument is true.
     */
    @Test(timeout = 4000)
    public void isFalse_withFalseValue_doesNotThrow() throws Throwable {
        Validate.isFalse(false);
    }
}
