package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.Duration;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hours_ESTest_test24 extends Hours_ESTest_scaffolding {

    /**
     * A sub-hour duration (1 nanosecond) cannot be expressed as a whole number
     * of hours, so {@link Hours#from(java.time.temporal.TemporalAmount)} must
     * reject it with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void from_durationOfOneNanosecond_throwsDateTimeException() throws Throwable {
        Duration oneNanosecond = Duration.ofNanos(1);

        try {
            Hours.from(oneNanosecond);
            fail("Expected DateTimeException: 1 nanosecond is not a whole number of hours");
        } catch (DateTimeException e) {
            // Message: "Amount could not be converted to a whole number of hours: 1 Nanos"
            verifyException("org.threeten.extra.Hours", e);
        }
    }
}
