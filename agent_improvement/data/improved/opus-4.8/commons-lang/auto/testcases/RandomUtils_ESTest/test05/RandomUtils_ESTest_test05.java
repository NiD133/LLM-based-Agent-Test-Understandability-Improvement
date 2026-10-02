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
public class RandomUtils_ESTest_test05 extends RandomUtils_ESTest_scaffolding {

    /**
     * When the inclusive start and exclusive end of the range are identical,
     * {@link RandomUtils#nextDouble(double, double)} has an empty range and
     * returns that single boundary value (here 0.0) rather than a random number.
     */
    @Test(timeout = 4000)
    public void nextDoubleWithEqualBoundsReturnsThatBound() throws Throwable {
        double result = RandomUtils.nextDouble(0.0, 0.0);

        assertEquals(0.0, result, 0.01);
    }
}
