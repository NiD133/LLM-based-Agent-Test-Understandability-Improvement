package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test14 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that a negative amount of minutes is first converted to a Duration
     * and then correctly expressed as milliseconds within an int.
     * <p>
     * -866 minutes = -866 * 60 * 1000 = -51,960,000 milliseconds, which fits in an int,
     * so {@link DurationUtils#toMillisInt(Duration)} returns the exact value.
     * </p>
     */
    @Test(timeout = 4000)
    public void toMillisInt_negativeMinutesDuration_returnsExactMilliseconds() throws Throwable {
        final long negativeMinutes = -866L;

        Duration duration = DurationUtils.toDuration(negativeMinutes, TimeUnit.MINUTES);
        int actualMillis = DurationUtils.toMillisInt(duration);

        final int expectedMillis = -51_960_000;
        assertEquals(expectedMillis, actualMillis);
    }
}
