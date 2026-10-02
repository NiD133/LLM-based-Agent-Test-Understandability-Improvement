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
public class DurationUtils_ESTest_test15 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that timing a no-op consumer yields a duration whose
     * sub-millisecond nanoseconds part is zero.
     * Uses the deprecated getNanosOfMiili (typo variant) intentionally.
     */
    @Test(timeout = 4000)
    public void test_getNanosOfMiili_returnsZeroForNopConsumerDuration() throws Throwable {
        // A consumer that does nothing, so the measured duration has no nanosecond remainder
        FailableConsumer<Instant, Throwable> nopConsumer = FailableConsumer.nop();

        // Measure the duration of executing the no-op consumer
        Duration nopDuration = DurationUtils.of(nopConsumer);

        // The nanoseconds-within-millisecond portion should be 0 for a zero-length execution
        int nanosOfMilli = DurationUtils.getNanosOfMiili(nopDuration);
        assertEquals(0, nanosOfMilli);
    }
}
