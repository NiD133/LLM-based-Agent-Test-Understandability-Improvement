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
public class Seconds_ESTest_test30 extends Seconds_ESTest_scaffolding {

    /**
     * Adding a negative-day {@link Duration} to {@link Seconds#ZERO} should yield
     * the equivalent number of seconds, computed as days * 86400.
     */
    @Test(timeout = 4000)
    public void plus_negativeDayDuration_convertsDurationToSeconds() throws Throwable {
        Duration negativeDays = Duration.ofDays(-1032L);

        Seconds result = Seconds.ZERO.plus((TemporalAmount) negativeDays);

        // -1032 days * 86400 seconds/day = -89164800 seconds
        assertEquals(-89164800, result.getAmount());
    }
}
