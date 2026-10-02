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
public class RandomUtils_ESTest_test12 extends RandomUtils_ESTest_scaffolding {

    // When startInclusive == endExclusive, the range is empty and nextInt must return startInclusive exactly.
    private static final int FIXED_BOUND = 1345069757;

    @Test(timeout = 4000)
    public void test12_nextIntWithEqualBoundsReturnsTheBoundValue() throws Throwable {
        int result = RandomUtils.nextInt(FIXED_BOUND, FIXED_BOUND);
        assertEquals("nextInt with equal bounds should return the bound value", FIXED_BOUND, result);
    }
}
