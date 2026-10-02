package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableRunnable;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test14 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that a negative duration in minutes is correctly converted to an int millisecond value.
     * -866 minutes * 60 seconds/minute * 1000 ms/second = -51,960,000 ms.
     */
    @Test(timeout = 4000)
    public void test_toMillisInt_negativeMinutesDuration_returnsExpectedMilliseconds() throws Throwable {
        Duration negativeMinutesDuration = DurationUtils.toDuration(-866L, TimeUnit.MINUTES);
        int millis = DurationUtils.toMillisInt(negativeMinutesDuration);
        assertEquals(-51960000, millis);
    }
}
