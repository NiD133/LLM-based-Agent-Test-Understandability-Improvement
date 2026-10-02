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
public class Seconds_ESTest_test22 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that Seconds.from() throws DateTimeException when given a Duration
     * that cannot be represented as a whole number of seconds.
     *
     * Duration.ofNanos(-2617) is internally stored as -1 second + 999997383 nanoseconds.
     * When Seconds.from() processes the NANOS unit, it detects the non-zero remainder
     * and rejects the conversion.
     */
    @Test(timeout = 4000)
    public void test_from_durationWithSubSecondNanos_throwsDateTimeException() throws Throwable {
        // -2617 nanoseconds has a sub-second nanosecond component that cannot be
        // expressed as a whole number of seconds, so conversion must fail.
        Duration durationWithSubSecondNanos = Duration.ofNanos(-2617L);

        try {
            Seconds.from(durationWithSubSecondNanos);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.Seconds", e);
        }
    }
}
