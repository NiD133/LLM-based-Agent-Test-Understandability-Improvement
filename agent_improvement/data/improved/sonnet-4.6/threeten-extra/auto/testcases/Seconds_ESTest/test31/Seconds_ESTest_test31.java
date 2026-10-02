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
public class Seconds_ESTest_test31 extends Seconds_ESTest_scaffolding {

    private static final int SECONDS_PER_HOUR = 3600;

    @Test(timeout = 4000)
    public void testMinusTemporalAmountEquivalentToSelfYieldsZeroAndOriginalIsUnchanged() throws Throwable {
        // 31 hours expressed as seconds
        Seconds thirtyOneHours = Seconds.ofHours(31);
        int expectedSeconds = 31 * SECONDS_PER_HOUR; // 111600

        // Convert to Duration — the same amount as a TemporalAmount
        Duration equivalentDuration = Duration.from(thirtyOneHours);

        // Subtracting an equivalent TemporalAmount should produce zero
        Seconds result = thirtyOneHours.minus((TemporalAmount) equivalentDuration);

        // Original is immutable and unchanged
        assertEquals(expectedSeconds, thirtyOneHours.getAmount());
        // Subtracting the full amount leaves zero
        assertTrue(result.isZero());
    }
}
