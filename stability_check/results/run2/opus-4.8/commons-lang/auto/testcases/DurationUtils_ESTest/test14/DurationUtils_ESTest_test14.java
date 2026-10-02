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
     * Verifies that a negative amount of minutes is converted into a Duration and
     * then into the equivalent number of milliseconds as an int.
     *
     * -866 minutes = -866 * 60 seconds * 1000 ms = -51,960,000 ms
     */
    @Test(timeout = 4000)
    public void toMillisInt_forNegativeMinutes_returnsEquivalentMilliseconds() throws Throwable {
        final long minutes = -866L;
        final Duration negativeDuration = DurationUtils.toDuration(minutes, TimeUnit.MINUTES);

        final int actualMillis = DurationUtils.toMillisInt(negativeDuration);

        final int expectedMillis = -51_960_000;
        assertEquals(expectedMillis, actualMillis);
    }
}
