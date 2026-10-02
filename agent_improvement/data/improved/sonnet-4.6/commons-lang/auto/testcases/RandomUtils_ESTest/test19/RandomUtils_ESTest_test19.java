package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomUtils_ESTest_test19 extends RandomUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        RandomUtils insecureRandom = RandomUtils.insecure();
        insecureRandom.toString();

        // Generate a random float in the full range [0, Float.MAX_VALUE)
        float randomFloat = insecureRandom.randomFloat();

        // Deprecated static nextLong() delegates to the secure RNG
        long secureRandomLong = RandomUtils.nextLong();

        insecureRandom.toString();

        // Generate a random long in a wide range via deprecated static method
        RandomUtils.nextLong(3927L, 8693331374357696304L);

        // Generate a random float with a very large upper bound derived from a long literal
        insecureRandom.randomFloat(0.0F, (float) 8693331374357696304L);

        // Degenerate range [0, 0) must return exactly 0
        long zeroRangeResult = RandomUtils.nextLong(0L, 0L);
    }
}
