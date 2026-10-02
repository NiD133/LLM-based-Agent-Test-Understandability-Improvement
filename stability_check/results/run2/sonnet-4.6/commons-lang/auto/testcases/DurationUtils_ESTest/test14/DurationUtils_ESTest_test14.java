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

    // -866 minutes × 60 seconds/min × 1000 ms/sec = -51,960,000 ms
    private static final long NEGATIVE_MINUTES = -866L;
    private static final int EXPECTED_MILLIS = -51960000;

    @Test(timeout = 4000)
    public void test_toMillisInt_negativeMinutesDuration_returnsCorrectMilliseconds() throws Throwable {
        Duration negativeDuration = DurationUtils.toDuration(NEGATIVE_MINUTES, TimeUnit.MINUTES);
        int milliseconds = DurationUtils.toMillisInt(negativeDuration);
        assertEquals(EXPECTED_MILLIS, milliseconds);
    }
}
