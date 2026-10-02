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
public class Weeks_ESTest_test23 extends Weeks_ESTest_scaffolding {

    /**
     * Adding a Duration to Weeks must fail if the Duration does not represent
     * a whole number of weeks (i.e. has a non-zero seconds remainder).
     * -705 days = -60 912 000 seconds, which is exactly 100 weeks and 5 days —
     * not a whole number of weeks, so DateTimeException is expected.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        Weeks zeroWeeks = Weeks.ZERO;
        Duration nonWholeWeeksDuration = Duration.ofDays(-705L);

        try {
            zeroWeeks.plus((TemporalAmount) nonWholeWeeksDuration);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Expected message: "Amount could not be converted to a whole number of weeks: -60912000 Seconds"
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
