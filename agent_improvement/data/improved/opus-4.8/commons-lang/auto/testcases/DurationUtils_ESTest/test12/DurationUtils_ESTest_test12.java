package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.time.Duration;
import org.apache.commons.lang3.function.FailableRunnable;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test12 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that timing an essentially instantaneous task yields a non-positive
     * Duration: {@link DurationUtils#of(FailableRunnable)} measures how long the
     * runnable takes to execute, and for a trivial no-op runnable the measured
     * duration is zero (or otherwise not greater than zero), so
     * {@link DurationUtils#isPositive(Duration)} returns {@code false}.
     */
    @Test(timeout = 4000)
    public void timingNoOpRunnableProducesNonPositiveDuration() throws Throwable {
        // A trivial runnable that performs no work, so timing it takes (effectively) no time.
        FailableRunnable<Throwable> noOpRunnable =
                (FailableRunnable<Throwable>) mock(FailableRunnable.class, new ViolatedAssumptionAnswer());

        Duration measuredDuration = DurationUtils.of(noOpRunnable);

        boolean positive = DurationUtils.isPositive(measuredDuration);

        assertFalse("Timing a no-op runnable should not produce a positive duration", positive);
    }
}
