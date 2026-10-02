package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.security.SecureRandom;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test07 extends RandomUtils_ESTest_scaffolding {

    // start (~1515) is greater than end (~390), which violates the range contract
    private static final double INVALID_START = 1515.7430316178;
    private static final double INVALID_END   = 390.8004;

    @Test(timeout = 4000)
    public void test_randomDouble_throwsWhenStartExceedsEnd() throws Throwable {
        RandomUtils secureRandom = RandomUtils.secure();
        try {
            secureRandom.randomDouble(INVALID_START, INVALID_END);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Start value must be smaller or equal to end value.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
