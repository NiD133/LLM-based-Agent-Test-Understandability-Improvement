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
public class DurationUtils_ESTest_test12 extends DurationUtils_ESTest_scaffolding {

    // A no-op runnable measured by DurationUtils.of() yields a duration that is not positive
    // because the mocked JVM clock does not advance during the mock's (empty) execution.
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        FailableRunnable<Throwable> noOpRunnable = (FailableRunnable<Throwable>) mock(FailableRunnable.class, new ViolatedAssumptionAnswer());
        Duration measuredDuration = DurationUtils.of(noOpRunnable);
        boolean isPositive = DurationUtils.isPositive(measuredDuration);
        assertFalse(isPositive);
    }
}
