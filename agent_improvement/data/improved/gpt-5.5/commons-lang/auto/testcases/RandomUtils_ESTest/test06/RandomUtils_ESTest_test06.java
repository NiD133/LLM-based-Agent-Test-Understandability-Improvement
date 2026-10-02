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
public class RandomUtils_ESTest_test06 extends RandomUtils_ESTest_scaffolding {

    private static final double NEGATIVE_START_INCLUSIVE = -1.0;
    private static final double END_EXCLUSIVE = 4913.0790244406835;

    @Test(timeout = 4000)
    public void randomDoubleRejectsNegativeStartValue() throws Throwable {
        RandomUtils randomUtils = new RandomUtils();

        try {
            randomUtils.randomDouble(NEGATIVE_START_INCLUSIVE, END_EXCLUSIVE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Both range values must be non-negative.
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
