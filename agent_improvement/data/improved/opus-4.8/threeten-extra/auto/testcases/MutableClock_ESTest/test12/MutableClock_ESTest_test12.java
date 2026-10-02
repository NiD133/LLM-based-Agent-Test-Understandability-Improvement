package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test12 extends MutableClock_ESTest_scaffolding {

    /**
     * Adding a zero-length duration to the clock should complete without error.
     */
    @Test(timeout = 4000)
    public void addZeroDurationSucceeds() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        TemporalAmount zeroDuration = Duration.ZERO;

        clock.add(zeroDuration);
    }
}
