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

    private static final long INITIAL_EPOCH_SECOND = -1034L;
    private static final long INITIAL_NANO_ADJUSTMENT = -1034L;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Instant initialInstant = MockInstant.ofEpochSecond(INITIAL_EPOCH_SECOND, INITIAL_NANO_ADJUSTMENT);
        ZoneOffset minimumZoneOffset = ZoneOffset.MIN;
        MutableClock clock = MutableClock.of(initialInstant, minimumZoneOffset);

        clock.set((TemporalAdjuster) initialInstant);
    }
}
