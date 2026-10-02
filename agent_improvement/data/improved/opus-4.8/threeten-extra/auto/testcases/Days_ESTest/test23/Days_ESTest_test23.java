package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test23 extends Days_ESTest_scaffolding {

    /**
     * Verifies that {@link Days#from(java.time.temporal.TemporalAmount)} rejects a
     * duration whose length is not an exact multiple of a day.
     * <p>
     * A duration of 1927 seconds cannot be expressed as a whole number of days,
     * so the conversion is expected to fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void from_durationNotWholeDays_throwsDateTimeException() throws Throwable {
        Duration nonWholeDayDuration = Duration.ofSeconds(1927L);

        try {
            Days.from(nonWholeDayDuration);
            fail("Expected a DateTimeException because 1927 seconds is not a whole number of days");
        } catch (DateTimeException expected) {
            // Message: "Amount could not be converted to a whole number of days: 1927 Seconds"
            verifyException("org.threeten.extra.Days", expected);
        }
    }
}
