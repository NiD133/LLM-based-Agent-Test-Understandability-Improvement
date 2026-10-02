package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test01 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that subtracting a {@link Duration} from a {@link Seconds} amount
     * produces a new, smaller amount while leaving the original unchanged.
     */
    @Test(timeout = 4000)
    public void subtractingDurationReturnsNewSmallerAmount() throws Throwable {
        // 396 hours expressed as seconds: 396 * 3600 = 1425600
        Seconds initialSeconds = Seconds.ofHours(396);
        Duration durationToSubtract = Duration.ofSeconds(396L);

        Seconds reducedSeconds = initialSeconds.minus((TemporalAmount) durationToSubtract);

        // The result is the original amount minus 396 seconds.
        assertEquals(1425204, reducedSeconds.getAmount());
        // The original instance is immutable and therefore unchanged.
        assertEquals(1425600, initialSeconds.getAmount());

        // Since the amounts differ, the two instances are not equal.
        assertFalse(initialSeconds.equals(reducedSeconds));
        assertFalse(reducedSeconds.equals(initialSeconds));
    }
}
