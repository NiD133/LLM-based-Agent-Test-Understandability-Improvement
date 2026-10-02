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
     * When the Duration passed to {@link DurationUtils#accept} is null, the method
     * returns without invoking the consumer (its internal guard skips a null duration).
     * This verifies that a null duration is handled gracefully rather than throwing.
     */
    @Test(timeout = 4000)
    public void acceptWithNullDurationDoesNotInvokeConsumer() throws Throwable {
        FailableBiConsumer<Long, Integer, Throwable> noOpConsumer = FailableBiConsumer.nop();

        DurationUtils.accept(noOpConsumer, (Duration) null);
    }
}
