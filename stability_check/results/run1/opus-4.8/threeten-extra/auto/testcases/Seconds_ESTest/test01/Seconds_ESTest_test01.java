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
     * Subtracting a Duration from a Seconds amount produces a new Seconds
     * reduced by that duration, while leaving the original amount unchanged,
     * and the two amounts are therefore not equal.
     */
    @Test(timeout = 4000)
    public void minusDurationReducesAmountAndIsNotEqualToOriginal() throws Throwable {
        // 396 hours expressed as seconds: 396 * 3600 = 1_425_600
        Seconds hoursAsSeconds = Seconds.ofHours(396);
        Duration durationToSubtract = Duration.ofSeconds(396L);

        Seconds reduced = hoursAsSeconds.minus((TemporalAmount) durationToSubtract);

        // Subtracting 396 seconds: 1_425_600 - 396 = 1_425_204
        assertEquals(1425204, reduced.getAmount());
        // The original amount is immutable and stays unchanged
        assertEquals(1425600, hoursAsSeconds.getAmount());

        // The reduced amount differs from the original, so equals is false both ways
        assertFalse(hoursAsSeconds.equals(reduced));
        assertFalse(reduced.equals((Object) hoursAsSeconds));
    }
}
