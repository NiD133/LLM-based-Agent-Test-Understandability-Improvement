package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test06 extends Days_ESTest_scaffolding {

    /**
     * Verifies that subtracting a negative {@link Duration} (which adds to the
     * amount) from a zero-day {@code Days} yields the expected positive number
     * of days, and that adding the result to an {@code Instant} produces a new
     * temporal value.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // A Days amount of zero, derived from the span between an instant and itself.
        Instant referenceInstant = MockInstant.ofEpochSecond(-2086L, -2086L);
        Days zeroDays = Days.between(referenceInstant, referenceInstant);
        assertTrue(zeroDays.isZero());

        // Subtracting -2086 days (a negative duration) is the same as adding 2086 days.
        Duration negativeDuration = Duration.ofDays(-2086L);
        Days resultDays = zeroDays.minus((TemporalAmount) negativeDuration);
        assertEquals(2086, resultDays.getAmount());

        // Adding the non-zero amount to the instant returns a different temporal object.
        Temporal shiftedInstant = resultDays.addTo(referenceInstant);
        assertNotSame(shiftedInstant, referenceInstant);
    }
}
