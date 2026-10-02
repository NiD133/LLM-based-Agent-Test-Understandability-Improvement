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
     * Verifies that a negative amount of minutes survives the round trip through
     * {@link DurationUtils#toDuration(long, TimeUnit)} and
     * {@link DurationUtils#toMillisInt(Duration)}.
     *
     * -866 minutes = -866 * 60 * 1000 = -51,960,000 milliseconds, which fits in an int.
     */
    @Test(timeout = 4000)
    public void toMillisInt_convertsNegativeMinutesToMilliseconds() throws Throwable {
        final long negativeMinutes = -866L;
        final Duration duration = DurationUtils.toDuration(negativeMinutes, TimeUnit.MINUTES);

        final int actualMillis = DurationUtils.toMillisInt(duration);

        final int expectedMillis = -51_960_000;
        assertEquals(expectedMillis, actualMillis);
    }
}
