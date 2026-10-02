package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.apache.commons.lang3.function.FailableConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test15 extends DurationUtils_ESTest_scaffolding {

    /**
     * Times a no-op task and verifies that the sub-millisecond nanosecond part
     * of the resulting Duration is reported as 0.
     *
     * <p>{@link DurationUtils#of(FailableConsumer)} runs the consumer and returns
     * how long it took. The no-op consumer does nothing, so the measured Duration
     * carries no whole-millisecond nanosecond remainder, and
     * {@link DurationUtils#getNanosOfMiili(Duration)} returns 0.</p>
     */
    @Test(timeout = 4000)
    public void nanosOfMilliForNoOpTaskDurationIsZero() throws Throwable {
        // Time the execution of a consumer that does nothing.
        FailableConsumer<Instant, Throwable> noOpTask = FailableConsumer.nop();
        Duration measuredDuration = DurationUtils.of(noOpTask);

        // The nanosecond-within-a-millisecond part of the duration should be 0.
        int nanosOfMilli = DurationUtils.getNanosOfMiili(measuredDuration);
        assertEquals(0, nanosOfMilli);
    }
}
