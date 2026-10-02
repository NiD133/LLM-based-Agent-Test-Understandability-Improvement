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
public class RandomUtils_ESTest_test19 extends RandomUtils_ESTest_scaffolding {

    /**
     * Exercises the insecure (ThreadLocalRandom-backed) instance together with the
     * static convenience methods, covering both the no-argument and the bounded
     * variants of the random-number generators without throwing.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Obtain the shared insecure instance and confirm its toString() works.
        RandomUtils insecureRandom = RandomUtils.insecure();
        insecureRandom.toString();

        // Instance-level float generation: full range, then a bounded range.
        insecureRandom.randomFloat();

        // Static long generation: unbounded, then within an explicit [start, end) range.
        RandomUtils.nextLong();
        insecureRandom.toString();

        long rangeStartInclusive = 3927L;
        long rangeEndExclusive = 8693331374357696304L;
        RandomUtils.nextLong(rangeStartInclusive, rangeEndExclusive);

        // Bounded float using the same large value (as a float) for the upper bound.
        insecureRandom.randomFloat(0.0F, (float) rangeEndExclusive);

        // When start == end, nextLong returns the start value (here, 0).
        RandomUtils.nextLong(0L, 0L);
    }
}
