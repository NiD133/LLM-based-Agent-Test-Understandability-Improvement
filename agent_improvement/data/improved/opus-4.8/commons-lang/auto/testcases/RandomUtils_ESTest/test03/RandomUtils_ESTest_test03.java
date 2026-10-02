package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test03 extends RandomUtils_ESTest_scaffolding {

    /**
     * {@link RandomUtils#nextFloat(float, float)} requires both range bounds to be
     * non-negative. Supplying a negative start value should make Validate reject the
     * call with an IllegalArgumentException ("Both range values must be non-negative.").
     */
    @Test(timeout = 4000)
    public void nextFloatWithNegativeStartThrowsIllegalArgumentException() throws Throwable {
        float negativeStart = -603.0F;
        float positiveEnd = 116058781.0F;

        try {
            RandomUtils.nextFloat(negativeStart, positiveEnd);
            fail("Expected IllegalArgumentException because the start value is negative");
        } catch (IllegalArgumentException e) {
            // The range check is performed by org.apache.commons.lang3.Validate.
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
