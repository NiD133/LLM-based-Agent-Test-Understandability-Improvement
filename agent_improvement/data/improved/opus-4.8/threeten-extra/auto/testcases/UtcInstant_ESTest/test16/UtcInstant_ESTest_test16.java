package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UtcInstant_ESTest_test16 extends UtcInstant_ESTest_scaffolding {

    /**
     * Subtracting the zero duration (the duration from an instant to itself)
     * yields an instant equal to the original, leaving its fields unchanged.
     */
    @Test(timeout = 4000)
    public void subtractingZeroDurationReturnsEqualInstant() throws Throwable {
        // The mocked clock is fixed, so this instant is deterministic.
        Instant now = MockInstant.now();
        UtcInstant originalInstant = UtcInstant.of(now);

        // Duration from an instant to itself is zero.
        Duration zeroDuration = originalInstant.durationUntil(originalInstant);
        UtcInstant resultInstant = originalInstant.minus(zeroDuration);

        assertTrue("Subtracting a zero duration must yield an equal instant",
                resultInstant.equals(originalInstant));

        long expectedModifiedJulianDay = 56702L;
        long expectedNanoOfDay = 73281320000000L;
        assertEquals(expectedModifiedJulianDay, originalInstant.getModifiedJulianDay());
        assertEquals(expectedNanoOfDay, resultInstant.getNanoOfDay());
    }
}
