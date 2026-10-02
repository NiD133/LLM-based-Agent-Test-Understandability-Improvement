package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test12 extends RandomUtils_ESTest_scaffolding {

    /**
     * When the inclusive start and exclusive end of the range are equal, the
     * range collapses to a single value, so {@link RandomUtils#nextInt(int, int)}
     * always returns that exact value rather than drawing a random number.
     */
    @Test(timeout = 4000)
    public void nextIntWithEmptyRangeReturnsBoundValue() throws Throwable {
        int singleBound = 1345069757;

        int result = RandomUtils.nextInt(singleBound, singleBound);

        assertEquals(singleBound, result);
    }
}
