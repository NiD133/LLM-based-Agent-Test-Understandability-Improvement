package org.jsoup.helper;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Validate_ESTest_test13 extends Validate_ESTest_scaffolding {

    /**
     * Verifies that isFalse(false) passes without throwing an exception,
     * since {@code false} satisfies the "must be false" constraint.
     */
    @Test(timeout = 4000)
    public void test_isFalse_withFalseValue_doesNotThrow() throws Throwable {
        // false satisfies the isFalse contract, so no ValidationException should be raised
        Validate.isFalse(false);
    }
}
