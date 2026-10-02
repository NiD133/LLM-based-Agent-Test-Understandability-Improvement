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
public class DurationUtils_ESTest_test00 extends DurationUtils_ESTest_scaffolding {

    private static final String PROPERTY_KEY_WITH_NO_CONFIGURED_VALUE = "g\"*8?I";
    private static final long DEFAULT_MILLIS = Long.MIN_VALUE;

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final Duration defaultDuration = DurationUtils.getMillis(PROPERTY_KEY_WITH_NO_CONFIGURED_VALUE, DEFAULT_MILLIS);
        final long actualMillis = DurationUtils.toMillisLong(defaultDuration);

        assertEquals(DEFAULT_MILLIS, actualMillis);
    }
}
