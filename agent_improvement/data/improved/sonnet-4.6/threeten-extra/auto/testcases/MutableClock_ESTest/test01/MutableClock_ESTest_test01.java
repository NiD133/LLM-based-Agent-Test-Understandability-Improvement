package org.threeten.extra;

import org.junit.Test;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjuster;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test01 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that MutableClock.set(TemporalAdjuster) accepts an Instant
     * as a TemporalAdjuster without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test_setWithInstantAsTemporalAdjuster_doesNotThrow() throws Throwable {
        // Instant implements TemporalAdjuster, so it can be used to adjust the clock
        Instant targetInstant = MockInstant.ofEpochSecond(-1034L, -1034L);
        ZoneOffset minOffset = ZoneOffset.MIN;
        MutableClock clock = MutableClock.of(targetInstant, minOffset);
        clock.set((TemporalAdjuster) targetInstant);
    }
}
