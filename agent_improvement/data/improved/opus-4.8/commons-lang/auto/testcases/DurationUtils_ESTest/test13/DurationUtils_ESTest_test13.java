package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test13 extends DurationUtils_ESTest_scaffolding {

    /**
     * When no system property matches the given key, {@link DurationUtils#getMillis(String, long)}
     * falls back to the supplied default (3831 ms here). A Duration of 3831 ms is greater than zero,
     * so {@link DurationUtils#isPositive(Duration)} should report it as positive.
     */
    @Test(timeout = 4000)
    public void getMillisDefaultDurationIsPositive() throws Throwable {
        final String unknownPropertyKey = "ctJ#Rib]z0+G8";
        final long defaultMillis = 3831L;

        Duration duration = DurationUtils.getMillis(unknownPropertyKey, defaultMillis);

        boolean positive = DurationUtils.isPositive(duration);
        assertTrue("A 3831 ms duration should be positive", positive);
    }
}
