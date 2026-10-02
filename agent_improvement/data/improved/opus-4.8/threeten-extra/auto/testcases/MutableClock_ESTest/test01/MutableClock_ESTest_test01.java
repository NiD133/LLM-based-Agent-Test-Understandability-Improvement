package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
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
     * Verifies that {@link MutableClock#set(TemporalAdjuster)} accepts an
     * {@link Instant} as the adjuster, since {@code Instant} implements
     * {@link TemporalAdjuster}. Adjusting the clock to a fixed instant should
     * complete without throwing.
     */
    @Test(timeout = 4000)
    public void set_withInstantAsAdjuster_doesNotThrow() throws Throwable {
        Instant initialInstant = MockInstant.ofEpochSecond(-1034L, -1034L);
        MutableClock clock = MutableClock.of(initialInstant, ZoneOffset.MIN);

        // Instant is a TemporalAdjuster, so it can be used to adjust the clock.
        clock.set((TemporalAdjuster) initialInstant);
    }
}
