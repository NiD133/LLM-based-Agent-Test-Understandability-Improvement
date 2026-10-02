package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test23 extends Weeks_ESTest_scaffolding {

    /**
     * Adding a Duration to a Weeks amount requires the duration to convert to a
     * whole number of weeks. A duration of -705 days is measured in seconds
     * (-60912000 seconds), which is not an exact multiple of a week, so plus(...)
     * must reject it with a DateTimeException.
     */
    @Test(timeout = 4000)
    public void plus_withDurationNotAWholeNumberOfWeeks_throwsDateTimeException() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Duration nonWeekAlignedDuration = Duration.ofDays(-705L);

        try {
            zeroWeeks.plus((TemporalAmount) nonWeekAlignedDuration);
            fail("Expected DateTimeException: -705 days is not a whole number of weeks");
        } catch (DateTimeException e) {
            // Message: "Amount could not be converted to a whole number of weeks: -60912000 Seconds"
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
