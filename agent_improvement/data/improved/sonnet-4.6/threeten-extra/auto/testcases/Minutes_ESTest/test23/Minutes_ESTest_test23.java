package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test23 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that adding a Duration whose total seconds are not divisible by 60
     * throws a DateTimeException, because Minutes requires a whole-minute conversion.
     *
     * Duration.ofSeconds(-906, -906) normalises to -907 seconds + 999,999,094 ns.
     * -907 seconds is not a whole number of minutes, so the conversion must fail.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        Minutes zero = Minutes.ZERO;

        // -906 seconds with a -906 ns adjustment normalises to -907 s (not divisible by 60)
        Duration nonMinuteAlignedDuration = Duration.ofSeconds(-906L, -906L);

        try {
            zero.plus((TemporalAmount) nonMinuteAlignedDuration);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected: "Amount could not be converted to a whole number of minutes: -907 Seconds"
            verifyException("org.threeten.extra.Minutes", e);
        }
    }
}
