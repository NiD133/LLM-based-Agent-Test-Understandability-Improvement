package org.threeten.extra;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test22 extends Seconds_ESTest_scaffolding {

    /**
     * A Duration whose length is not a whole number of seconds (here, a
     * sub-second amount of -2617 nanoseconds) cannot be represented as
     * {@link Seconds}. Converting it with {@link Seconds#from} must therefore
     * fail with a {@link DateTimeException}.
     */
    @Test(timeout = 4000)
    public void from_subSecondDuration_throwsDateTimeException() throws Throwable {
        Duration subSecondDuration = Duration.ofNanos(-2617L);

        try {
            Seconds.from(subSecondDuration);
            fail("Expected DateTimeException: a sub-second duration is not a whole number of seconds");
        } catch (DateTimeException expected) {
            assertTrue(
                    "Exception should explain the conversion failure, but was: " + expected.getMessage(),
                    expected.getMessage().contains("could not be converted to a whole number of seconds"));
        }
    }
}
