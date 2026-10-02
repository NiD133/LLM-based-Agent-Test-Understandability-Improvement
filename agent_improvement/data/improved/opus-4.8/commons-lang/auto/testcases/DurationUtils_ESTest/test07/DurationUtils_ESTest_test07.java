package org.apache.commons.lang3.time;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DurationUtils_ESTest_test07 extends DurationUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link DurationUtils#isPositive(Duration)} returns
     * {@code false} for a negative duration.
     */
    @Test(timeout = 4000)
    public void isPositive_returnsFalse_forNegativeDuration() throws Throwable {
        // A duration of -105 seconds (with an additional -105 nanoseconds) is negative.
        Duration negativeDuration = Duration.ofSeconds(-105L, -105L);

        boolean positive = DurationUtils.isPositive(negativeDuration);

        assertFalse(positive);
    }
}
