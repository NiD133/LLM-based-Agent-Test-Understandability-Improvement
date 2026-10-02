package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MutableClock_ESTest_test00 extends MutableClock_ESTest_scaffolding {

    /**
     * Verifies that {@link MutableClock#set(TemporalField, long)} accepts a
     * supported field and completes without throwing. The clock starts at the
     * epoch (1970-01-01T00:00:00Z), so setting the milli-of-second to 0 leaves
     * the instant unchanged.
     */
    @Test(timeout = 4000)
    public void setMilliOfSecondToZeroSucceeds() throws Throwable {
        MutableClock clock = MutableClock.epochUTC();
        TemporalField milliOfSecond = ChronoField.MILLI_OF_SECOND;

        clock.set(milliOfSecond, 0L);
    }
}
