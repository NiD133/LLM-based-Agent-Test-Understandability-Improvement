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
public class DurationUtils_ESTest_test09 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#accept} does not throw when the
     * supplied {@link Duration} argument is {@code null}.
     * The method's guard condition ({@code consumer != null && duration != null})
     * should short-circuit the consumer call, so a no-op consumer paired with a
     * null duration must complete silently.
     */
    @Test(timeout = 4000)
    public void test_accept_withNullDuration_doesNotThrow() throws Throwable {
        FailableBiConsumer<Long, Integer, Throwable> noOpConsumer = FailableBiConsumer.nop();
        DurationUtils.accept(noOpConsumer, (Duration) null);
    }
}
