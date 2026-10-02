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
public class DurationUtils_ESTest_test17 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#accept(FailableBiConsumer, Duration)} runs without
     * error when given a no-op consumer and a Duration built from a system-property lookup.
     *
     * <p>The key "_\"/5Q'" does not name an existing system property, so
     * {@link DurationUtils#getSeconds(String, long)} falls back to the default of -913 seconds.
     * The no-op consumer ignores the duration, so the call simply completes.</p>
     */
    @Test(timeout = 4000)
    public void accept_withNoOpConsumer_completesForPropertyBackedDuration() throws Throwable {
        final String missingPropertyKey = "_\"/5Q'";
        final long defaultSeconds = -913L;

        Duration duration = DurationUtils.getSeconds(missingPropertyKey, defaultSeconds);

        FailableBiConsumer<Long, Integer, Throwable> noOpConsumer = FailableBiConsumer.nop();
        DurationUtils.accept(noOpConsumer, duration);
    }
}
