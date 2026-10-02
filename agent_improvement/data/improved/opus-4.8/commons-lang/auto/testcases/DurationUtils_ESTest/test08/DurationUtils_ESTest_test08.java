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
public class DurationUtils_ESTest_test08 extends DurationUtils_ESTest_scaffolding {

    /**
     * When the property key is null, {@link DurationUtils#getMillis(String, long)}
     * cannot look up a system property, so it falls back to the supplied default
     * value (here, -1048 milliseconds) and returns a non-null Duration built from it.
     */
    @Test(timeout = 4000)
    public void getMillisWithNullKeyReturnsDurationFromDefault() throws Throwable {
        final String nullPropertyKey = null;
        final long defaultMillis = -1048L;

        Duration result = DurationUtils.getMillis(nullPropertyKey, defaultMillis);

        assertNotNull(result);
    }
}
