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
     * Adding a {@link Duration} that does not represent a whole number of
     * minutes must fail, because {@link Minutes} can only hold whole minutes.
     * Here the duration is -906 seconds plus -906 nanoseconds, i.e. roughly
     * -907 seconds with a leftover fraction, which cannot be expressed as an
     * exact number of minutes.
     */
    @Test(timeout = 4000)
    public void plus_withDurationNotAWholeNumberOfMinutes_throwsDateTimeException() throws Throwable {
        Minutes zeroMinutes = Minutes.ZERO;
        Duration fractionalDuration = Duration.ofSeconds(-906L, -906L);

        try {
            zeroMinutes.plus((TemporalAmount) fractionalDuration);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            // Amount could not be converted to a whole number of minutes: -907 Seconds
            verifyException("org.threeten.extra.Minutes", e);
        }
    }
}
