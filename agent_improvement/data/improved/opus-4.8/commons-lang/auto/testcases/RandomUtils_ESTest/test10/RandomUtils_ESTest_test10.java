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
public class RandomUtils_ESTest_test10 extends RandomUtils_ESTest_scaffolding {

    /**
     * When the inclusive start and exclusive end of the range are identical,
     * {@link RandomUtils#nextFloat(float, float)} short-circuits and returns
     * that single boundary value rather than generating a random number.
     */
    @Test(timeout = 4000)
    public void nextFloatWithEqualBoundsReturnsThatBound() throws Throwable {
        float bound = 3.9726779E18F;

        float result = RandomUtils.nextFloat(bound, bound);

        assertEquals(bound, result, 0.01F);
    }
}
