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
public class DurationUtils_ESTest_test11 extends DurationUtils_ESTest_scaffolding {

    private static final String PROPERTY_KEY = "_\"/5Q'";
    private static final long DEFAULT_DURATION_AMOUNT = -210L;

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        final TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;
        final ChronoUnit convertedUnit = DurationUtils.toChronoUnit(defaultTimeUnit);

        final Duration configuredDuration = DurationUtils.get(PROPERTY_KEY, convertedUnit, DEFAULT_DURATION_AMOUNT);

        assertNotNull(configuredDuration);
    }
}
