package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Tests that Days.minus(Duration) negates the duration's day count when applied to a zero Days instance,
     * and that addTo() returns a new Temporal object rather than the original.
     *
     * Setup:
     *  - referenceInstant: an Instant used as both start and end of a between() call, yielding zero days.
     *  - negativeDuration: a Duration of -2086 days used to subtract from the zero Days instance.
     *
     * Expected:
     *  - zeroDays.isZero() is true (between same instant = 0 days)
     *  - zeroDays.minus(-2086 days) = +2086 days (subtracting a negative = adding)
     *  - addTo() returns a distinct Temporal object, not the same reference
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Create a reference instant; using the same instant for both bounds gives zero days
        Instant referenceInstant = MockInstant.ofEpochSecond((-2086L), (-2086L));
        Days zeroDays = Days.between(referenceInstant, referenceInstant);

        // Subtracting a negative duration flips the sign: 0 - (-2086) = +2086
        Duration negativeDuration = Duration.ofDays((-2086L));
        Days positiveDays = zeroDays.minus((TemporalAmount) negativeDuration);

        // addTo() must return a new Temporal shifted by 2086 days, not the original reference
        Temporal shiftedInstant = positiveDays.addTo(referenceInstant);
        assertNotSame(shiftedInstant, referenceInstant);

        // Verify intermediate state: between the same instant is always zero
        assertTrue(zeroDays.isZero());

        // Verify the subtraction of a negative duration produced the expected positive count
        assertEquals(2086, positiveDays.getAmount());
    }
}
